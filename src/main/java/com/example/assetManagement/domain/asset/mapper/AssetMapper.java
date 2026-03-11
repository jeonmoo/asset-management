package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    Asset toAsset(AssetCreateRequest request);
}
