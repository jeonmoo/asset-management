package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.AssetDetailResponse;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignFormResponse;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    AssetDetailResponse toAssetDetailResponse(Asset asset, List<AssetHistory> histories);

    AssetAssignFormResponse toAssetAssignFormResponse(Asset asset);
}
