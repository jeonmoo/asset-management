package com.example.assetManagement.config;

import com.example.assetManagement.domain.member.MemberRepository;
import com.example.assetManagement.domain.member.entity.Members;
import com.example.assetManagement.domain.member.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 이미 관리자가 있는지 확인 후 생성
            if (memberRepository.findByLoginId("admin@company.com").isEmpty()) {
                Members admin = Members.builder()
                        .loginId("admin@company.com")
                        .password(passwordEncoder.encode("admin1234"))
                        .role(MemberRole.ADMIN)
                        .build();
                memberRepository.save(admin);
            }
        };
    }
}
