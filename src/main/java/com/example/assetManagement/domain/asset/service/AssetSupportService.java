package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.common.exception.GlobalException;
import com.example.assetManagement.common.exception.exceptionCode.AssetExceptionCode;
import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import com.example.assetManagement.domain.asset.repository.assetHistory.AssetHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetSupportService {

    private final AssetRepository assetRepository;
    private final AssetHistoryRepository assetHistoryRepository;

    public Asset mapToAsset(AssetCreateRequest request) {
        return Asset.builder()
                .assetNo(request.getAssetNo())
                .name(request.getName())
                .category(request.getCategory())
                .serialNo(request.getSerialNo())
                .purchasedAt(request.getPurchasedAt().atStartOfDay())
                .memo(request.getMemo())
                .build();
    }

    public AssetHistory mapToAssetHistory(Asset asset, AssetAssignRequest request) {
        return AssetHistory.builder()
                .asset(asset)
                .assigneeName(request.getAssigneeName())
                .assigneeEmail(request.getAssigneeEmail())
                .department(request.getDepartment())
                .note(request.getNote())
                .build();
    }

    public void validateCreate(String assetNo) {
        Boolean isDuplicated = assetRepository.existsByAssetNo(assetNo);
        if (isDuplicated) {
            throw new GlobalException(AssetExceptionCode.EXISTS_ASSET_NO);
        }
    }

    public void validateModify(Long assetId, String assetNo) {
        Boolean isDuplicated = assetRepository.existsByIdAndAssetNoNot(assetId, assetNo);
        if (isDuplicated) {
            throw new GlobalException(AssetExceptionCode.EXISTS_ASSET_NO);
        }
    }

    public void assignAsset(Asset asset, AssetAssignRequest request) {
        validateAssign(asset);
        asset.assign();
        AssetHistory assetHistory = mapToAssetHistory(asset, request);
        assetHistoryRepository.save(assetHistory);
    }

    private void validateAssign(Asset asset) throws GlobalException {
        if (asset.getStatus() != AssetStatus.IN_STOCK) {
            throw new GlobalException(AssetExceptionCode.NOT_AVAILABLE_ASSET);
        }
    }

    public void returnAsset(Asset asset) {
        validateReturn(asset);
        AssetHistory history = assetHistoryRepository.findByAssetIdAndReturnedAtIsNull(asset.getId());

        history.returnAssetHistory();
        asset.returnAsset();
    }

    private void validateReturn(Asset asset) {
        if (asset.getStatus() != AssetStatus.ASSIGNED) {
            throw new GlobalException(AssetExceptionCode.ASSET_NOT_ASSIGNED);
        }
    }

}
