package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.AssetDetailResponse;
import com.example.assetManagement.domain.asset.entity.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    AssetDetailResponse toAssetDetailResponse(Asset asset);
}
