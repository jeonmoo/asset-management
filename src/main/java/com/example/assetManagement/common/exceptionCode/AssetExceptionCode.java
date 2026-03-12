package com.example.assetManagement.common.exceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetExceptionCode implements ExceptionCode {
    NOT_FOUND_ASSET("없는 자산 입니다."),
    ;

    private final String message;
}
