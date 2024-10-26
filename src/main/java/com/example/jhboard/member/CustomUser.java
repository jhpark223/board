package com.example.jhboard.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    public String displayName;
    public Long id;

    public CustomUser(
            String username, //유저 id
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
