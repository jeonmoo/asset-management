package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.common.exception.GlobalException;
import com.example.assetManagement.common.exception.exceptionCode.AssetExceptionCode;
import com.example.assetManagement.domain.asset.dto.*;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignFormResponse;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import com.example.assetManagement.domain.asset.mapper.AssetMapper;
import com.example.assetManagement.domain.asset.repository.AssetQueryRepository;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import com.example.assetManagement.domain.asset.repository.assetHistory.AssetHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetService {

    private final AssetSupportService assetSupportService;

    private final AssetRepository assetRepository;
    private final AssetQueryRepository assetQueryRepository;
    private final AssetHistoryRepository assetHistoryRepository;

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
    public AssetDetailResponse getAssetDetail(Long assetId) {
        Asset asset = findById(assetId);
        List<AssetHistory> histories = assetHistoryRepository.findByAssetIdOrderByAssignedAtDesc(assetId);
        AssetHistory currentHistory = assetSupportService.getCurrentAssignHistory(histories).orElse(null);
        return assetMapper.toAssetDetailResponse(asset, histories, currentHistory);
    }

    public Asset createAsset(AssetCreateRequest request) {
        assetSupportService.validateCreate(request.getAssetNo());
        Asset asset = assetMapper.toAsset(request);
        return assetRepository.save(asset);
    }

    public void modifyAsset(Long assetId, AssetModifyRequest request) {
        assetSupportService.validateModify(assetId, request.getAssetNo());

        Asset asset = findWithLockById(assetId);

        String assetNo = request.getAssetNo();
        String serialNo = request.getSerialNo();
        String name = request.getName();
        Category category = request.getCategory();
        LocalDate purchasedAt = request.getPurchasedAt();
        String memo = request.getMemo();
        asset.updateInfo(assetNo, serialNo, name, category, purchasedAt, memo);
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

    public Asset assignAsset(Long assetId, AssetAssignRequest request) {
        Asset asset = findWithLockById(assetId);
        assetSupportService.assignAsset(asset, request);
        return asset;
    }

    public void returnAsset(Long assetId) {
        Asset asset = findWithLockById(assetId);
        assetSupportService.returnAsset(asset);
    }

    public void repairAsset(Long assetId) {
        Asset asset = findWithLockById(assetId);
        if (asset.getStatus() == AssetStatus.ASSIGNED) {
            assetSupportService.returnAsset(asset);
        }
        asset.changeStatusToRepair();
    }

    public void completeRepairAsset(Long assetId) {
        Asset asset = findWithLockById(assetId);
        assetSupportService.validateRepairAsset(asset);
        asset.completeRepair();
    }
}
