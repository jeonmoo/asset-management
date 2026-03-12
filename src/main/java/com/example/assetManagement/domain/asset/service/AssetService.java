package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.AssetListResponse;
import com.example.assetManagement.domain.asset.dto.AssetSearchCondition;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.mapper.AssetMapper;
import com.example.assetManagement.domain.asset.repository.AssetQueryRepository;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetQueryRepository assetQueryRepository;
    private final AssetMapper assetMapper;

    @Transactional(readOnly = true)
    public Page<AssetListResponse> getAssets(AssetSearchCondition condition, Pageable pageable) {
        return assetQueryRepository.findByAsset(condition, pageable);
    }

    public void registerAsset(AssetCreateRequest request) {
        Asset asset = mapToEntity(request);
        assetRepository.save(asset);
    }

    private Asset mapToEntity(AssetCreateRequest request) {
        return Asset.builder()
                .assetNo(request.getAssetNo())
                .name(request.getName())
                .category(request.getCategory())
                .serialNo(request.getSerialNo())
                .purchasedAt(request.getPurchasedAt())
                .memo(request.getMemo())
                .build();
    }

    public void deleteAsset(Long assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(IllegalArgumentException::new);

        softDeleteAsset(asset);
    }

    private void softDeleteAsset(Asset asset) {
        asset.setIsDelete(true);
        asset.setDeletedAt(LocalDateTime.now());
    }


}
