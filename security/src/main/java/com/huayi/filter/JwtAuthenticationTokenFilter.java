package com.huayi.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.huayi.config.SecurityConfigProperties;
import com.huayi.util.Result;
import com.huayi.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author gjw
 * @Date 2021/3/16 9:42
 **/
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String tokenHeader = "token";

    @Autowired
    private SecurityConfigProperties configProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        PathMatcher pathMatcher = new AntPathMatcher();
        AtomicReference<Boolean> isAllowAccess = new AtomicReference<>(false);

        configProperties.getAllowPath().stream().filter(o -> o!=null).forEach( o ->{
            if(pathMatcher.match(o,request.getRequestURI())){
                isAllowAccess.set(true);
            }
        });

        if(isAllowAccess.get()){
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(this.tokenHeader);
        isAllowAccess.set(verifyToken( token,  response));
        if(!isAllowAccess.get()){return;}

        if (token != null ) {
            String username = jwtTokenUtil.getUserNameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean verifyToken(String token, HttpServletResponse response) throws IOException {

        if(StrUtil.isNotBlank(token) && jwtTokenUtil.isTokenExpired(token) ){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            Result result = new Result(Result.Status.TokenExpire);
            response.getWriter().println( JSONUtil.parse(result) );
            response.getWriter().flush();
            return false;
        }

        return true;
    }

}
