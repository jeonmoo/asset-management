package com.example.assetManagement.config.auth;

import com.example.assetManagement.domain.member.entity.Members;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PrincipalDetails implements UserDetails {
    private final Members members; // 엔티티 포함

    public PrincipalDetails(Members members) { this.members = members; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(members.getRole().name()));
    }

    @Override
    public String getPassword() {
        return members.getPassword();
    }

    @Override
    public String getUsername() {
        return members.getLoginId();
    }

}
