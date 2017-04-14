package com.ronbreier.security;

import com.ronbreier.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by Ron Breier on 4/7/2017.
 * UserDetails Class handles only the user information
 * needed by Spring Security
 */

public class CustomUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = 1l;

    private List<String> userRoles;

    private User user;

    public CustomUserDetails(User user, List<String> userRoles){
        super(user);
        this.user = user;
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername(){
        return super.getUsername();
    }

    public User getUser(){
        return user;
    }

}