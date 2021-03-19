package com.huayi.component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huayi.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Author gjw
 * @Date 2021/3/16 10:08
 **/
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserDetails implements UserDetails{

    private SysUser sysUser;

    public AuthUserDetails(SysUser user){
        this.sysUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.sysUser.getMenus().stream()
                .filter(menu -> menu.getAuthorities()!=null)
                .map(menu ->new SimpleGrantedAuthority(menu.getAuthorities()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.sysUser.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.sysUser.getDeleted() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }
}
