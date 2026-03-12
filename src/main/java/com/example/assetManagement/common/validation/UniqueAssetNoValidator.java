package com.example.assetManagement.common.validation;

import com.example.assetManagement.common.annotation.UniqueAssetNo;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueAssetNoValidator implements ConstraintValidator<UniqueAssetNo, String> {
    private final AssetRepository assetRepository;

    @Override
    public boolean isValid(String assetNo, ConstraintValidatorContext context) {
        return !assetRepository.existsByAssetNo(assetNo);
    }
}
