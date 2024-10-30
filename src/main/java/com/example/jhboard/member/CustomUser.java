package com.example.jhboard.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User { //User클래스는 UserDetails 인터페이스를 구현한 클래스
    public String displayName; //extends한 User클래스에는 없는 displayName과 id를 추가
    public Long id;

    public CustomUser(
            String username, //유저 id
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
