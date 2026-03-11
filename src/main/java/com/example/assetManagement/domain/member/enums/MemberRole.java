package com.example.assetManagement.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    ADMIN("관리자"),
    USER("사용자"),
    ;

    private final String description;
}
