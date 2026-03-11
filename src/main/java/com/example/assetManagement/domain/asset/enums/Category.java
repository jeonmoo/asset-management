package com.example.assetManagement.domain.asset.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    LAPTOP("노트북"),
    MONITOR("모니터"),
    PHONE("휴대폰"),
    ETC("기타"),
    ;

    private final String description;
}
