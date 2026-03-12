package com.example.assetManagement.common.annotation;

import com.example.assetManagement.common.validation.UniqueAssetNoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueAssetNoValidator.class)
public @interface UniqueAssetNo {
    String message() default "이미 존재하는 자산 번호입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
