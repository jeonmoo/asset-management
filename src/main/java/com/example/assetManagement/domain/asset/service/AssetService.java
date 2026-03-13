package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.common.exception.GlobalException;
import com.example.assetManagement.common.exception.exceptionCode.AssetExceptionCode;
import com.example.assetManagement.domain.asset.dto.*;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignFormResponse;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
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

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService {

    private final AssetSupportService assetSupportService;
    private final AssetRepository assetRepository;
    private final AssetQueryRepository assetQueryRepository;
    private final AssetMapper assetMapper;

    private Asset findWithLockById(Long assetId) {
        return assetRepository.findWithLockById(assetId)
                .orElseThrow(() -> new GlobalException(AssetExceptionCode.NOT_FOUND_ASSET));
    }

    private Asset findById(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new GlobalException(AssetExceptionCode.NOT_FOUND_ASSET));
    }

    @Transactional(readOnly = true)
    public Page<AssetListResponse> getAssets(AssetSearchCondition condition, Pageable pageable) {
        return assetQueryRepository.findByAsset(condition, pageable);
    }

    @Transactional(readOnly = true)
    public AssetDetailResponse getAsset(Long assetId) {
        Asset asset = findById(assetId);
        return assetMapper.toAssetDetailResponse(asset);
    }

    public void registerAsset(AssetCreateRequest request) {
        Asset asset = assetSupportService.mapToAsset(request);
        assetRepository.save(asset);
    }

    public void modifyAsset(Long assetId, AssetModifyRequest request) {
        Asset asset = findWithLockById(assetId);

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
        Asset asset = findWithLockById(assetId);
        asset.softDelete();
    }

    @Transactional(readOnly = true)
    public AssetAssignFormResponse getAssetWithAssignForm(Long assetId) {
        Asset asset = findById(assetId);
        return assetMapper.toAssetAssignFormResponse(asset);
    }

    public void assignAsset(Long assetId, AssetAssignRequest request) {
        Asset asset = findWithLockById(assetId);
        assetSupportService.assignAsset(asset, request);
    }

    public void returnAsset(Long assetId) {
        Asset asset = findWithLockById(assetId);
        assetSupportService.returnAsset(asset);
    }
}
