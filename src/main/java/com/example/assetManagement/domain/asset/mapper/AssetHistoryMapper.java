package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetHistoryMapper {

    AssetHistory toAssetHistory(Asset asset, AssetAssignRequest request);
}
