package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.domain.asset.dto.*;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import com.example.assetManagement.domain.asset.mapper.AssetMapper;
import com.example.assetManagement.domain.asset.repository.AssetQueryRepository;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService {

    private final AssetSupportService assetSupportService;
    private final AssetRepository assetRepository;
    private final AssetQueryRepository assetQueryRepository;
    private final AssetMapper assetMapper;

    @Transactional(readOnly = true)
    public Page<AssetListResponse> getAssets(AssetSearchCondition condition, Pageable pageable) {
        return assetQueryRepository.findByAsset(condition, pageable);
    }

    public AssetDetailResponse getAsset(Long assetId) {
        Asset asset = assetRepository.findById(assetId).orElseThrow(IllegalArgumentException::new);
        return assetMapper.toAssetDetailResponse(asset);
    }

    public void registerAsset(AssetCreateRequest request) {
        Asset asset = assetSupportService.mapToEntity(request);
        assetRepository.save(asset);
    }

    public void modifyAsset(Long assetId, AssetModifyRequest request) {
        Asset asset = assetRepository.findWithLockById(assetId).orElseThrow(IllegalArgumentException::new);

        String assetNo = request.getAssetNo();
        String serialNo = request.getSerialNo();
        String name = request.getName();
        Category category = request.getCategory();
        AssetStatus status = request.getStatus();
        LocalDate purchasedAt = request.getPurchasedAt();
        String memo = request.getMemo();
        asset.updateInfo(assetNo, serialNo, name, category, status, purchasedAt, memo);
    }

    public void deleteAsset(Long assetId) {
        Asset asset = assetRepository.findWithLockById(assetId)
                .orElseThrow(IllegalArgumentException::new);

        asset.softDelete();
    }
}
