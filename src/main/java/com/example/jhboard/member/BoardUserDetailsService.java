package com.example.jhboard.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = memberRepository.findByUsername(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("not found");
        }
        var user = result.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("normalUser"));

        //UserDetails 인터페이스는 사용자명,비밀번호,권한 정보만을 가지고 있음
        var a = new CustomUser(user.getUsername(),user.getPassword(), authorities); //기본 User클래스를 상속받아서 displayName과 id를 추가한 CustomUser클래스를 사용
        a.displayName = user.getDisplayName();
        a.id = user.getId(); //auth변수에서 id를 가져오기 위해 추가
        return a; //UserDetails 인터페이스를 구현한 CustomUser객체를 반환
    }



}
