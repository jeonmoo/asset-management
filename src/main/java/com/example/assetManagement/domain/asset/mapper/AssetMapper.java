package com.example.assetManagement.domain.asset.mapper;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.AssetDetailResponse;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignFormResponse;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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

    @Mapping(target = "id", source = "asset.id")
    @Mapping(target = "createdAt", source = "asset.createdAt")
    @Mapping(target = "updatedAt", source = "asset.updatedAt")
    @Mapping(target = "assigneeName", source = "currentHistory.assigneeName")
    @Mapping(target = "histories", source = "histories")
    AssetDetailResponse toAssetDetailResponse(Asset asset, List<AssetHistory> histories, AssetHistory currentHistory);

    @Mapping(target = "assigneeName", ignore = true)
    @Mapping(target = "assigneeEmail", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "note", ignore = true)
    AssetAssignFormResponse toAssetAssignFormResponse(Asset asset);
}
