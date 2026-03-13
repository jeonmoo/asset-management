package com.example.assetManagement.common.exception.exceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetExceptionCode implements ExceptionCode {
    NOT_FOUND_ASSET("없는 자산 입니다."),
    NOT_AVAILABLE_ASSET("보유중인 자산이 아닙니다."),
    ASSET_NOT_ASSIGNED("할당 된 자산이 아닙니다."),
    EXISTS_ASSET_NO("이미 등록된 자산 번호 입니다."),
    ;

    private final String message;
}
