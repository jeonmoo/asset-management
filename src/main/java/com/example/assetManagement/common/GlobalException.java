package com.example.assetManagement.common;

import com.example.assetManagement.common.exceptionCode.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private String message;

    public GlobalException(ExceptionCode exception) {
        this.message = exception.getMessage();
    }
}
