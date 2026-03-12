package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.AssetModifyRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetSupportService {

    public Asset mapToEntity(AssetCreateRequest request) {
        return Asset.builder()
                .assetNo(request.getAssetNo())
                .name(request.getName())
                .category(request.getCategory())
                .serialNo(request.getSerialNo())
                .purchasedAt(request.getPurchasedAt())
                .memo(request.getMemo())
                .build();
    }
}
