package com.huayi.component;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.huayi.config.SecurityConfigProperties;
import com.huayi.entity.SysUser;
import com.huayi.mapper.SysUserMapper;
import com.huayi.util.Result;
import com.huayi.util.exception.WebException;
import com.huayi.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author gjw
 * @Date 2021/3/16 10:07
 **/
@Component
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String tokenGroup = "token::";

    @Autowired
    private SecurityConfigProperties securityConfigProperties;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if( StrUtil.isBlank( username) ) return null;

        String tokenKey = getTokenKey(username);

        ValueOperations valueOp = redisTemplate.opsForValue();

        SysUser sysUser = (SysUser)valueOp.get( tokenKey );

        if(sysUser == null){
            sysUser = sysUserMapper.selectUserByUsername(username);
        }
        AuthUserDetails details = new AuthUserDetails();

        if(sysUser != null){
            details = new AuthUserDetails(sysUser);
            valueOp.set(tokenKey,sysUser,securityConfigProperties.getExpiration(), TimeUnit.MILLISECONDS);
        }

        return details;
    }




    /**
     * 注册用户
     * */
    public boolean register(String username,String password){

        if( StrUtil.isAllNotBlank(username,password) ){

            //查询是否已被注册
            LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SysUser::getName,username);
            SysUser sysUser = sysUserMapper.selectOne(wrapper);

            if( sysUser != null ){
                throw new WebException(Result.Status.Fail,"该用户已被注册");
            }
            //添加用户
            SysUser newUser = new SysUser();
            newUser.setName(username);
            newUser.setPassword( passwordEncoder.encode( password ) );
            int insert = sysUserMapper.insert(newUser);

            return insert > 0;
        }

        return false;
    }


    /** 登录颁发token */
    public String login(String username,String password){

        UserDetails userDetails = loadUserByUsername(username);

        if(null == userDetails  || ( !userDetails.isEnabled() ) ){
            throw new UsernameNotFoundException("用户名不存在");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("密码不正确");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        flushUserCache(userDetails.getUsername());

        return token;
    }

    /**获取token的缓存key*/
    public String getTokenKey(String username){
        return  tokenGroup + username;
    }

    public void flushUserCache(String username){
        redisTemplate.delete( getTokenKey(username) );
        loadUserByUsername(username);

    }


}
