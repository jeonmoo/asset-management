package com.example.assetManagement.domain.asset.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AssetListResponse {

    private Long id;
    private String assetNo;
    private String name;
    private String category;
    private String status;
    private LocalDateTime createdAt;

    @QueryProjection // 빌드 시 QAssetListResponse 생성
    public AssetListResponse(Long id, String assetNo, String name, String category, String status, LocalDateTime createdAt) {
        this.id = id;
        this.assetNo = assetNo;
        this.name = name;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
    }
}
