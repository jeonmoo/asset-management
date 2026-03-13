package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.AssetDetailResponse;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignFormResponse;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(target = "purchasedAt", source = "purchasedAt", qualifiedByName = "localDateToLocalDateTime")
    Asset toAsset(AssetCreateRequest request);

    @Named("localDateToLocalDateTime")
    default LocalDateTime localDateToLocalDateTime(LocalDate date) {
        if (date == null) return null;
        return date.atStartOfDay(); // 00:00:00으로 변환
    }

    AssetDetailResponse toAssetDetailResponse(Asset asset, List<AssetHistory> histories);

    AssetAssignFormResponse toAssetAssignFormResponse(Asset asset);
}
