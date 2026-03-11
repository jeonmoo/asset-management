package com.example.assetManagement.domain.asset.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetStatus {

    IN_STOCK("보유중"),
    ASSIGNED("지급"),
    REPAIR("수리중"),
    DISCARDED("폐기"),
    ;

    private final String description;

}
