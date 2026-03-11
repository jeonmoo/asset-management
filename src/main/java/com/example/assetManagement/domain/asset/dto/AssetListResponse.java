package com.example.assetManagement.domain.asset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder
public class AssetListResponse {

    private String assetNo;
    private String name;
    private String category;
    private String status;
    private LocalDate purchasedAt;
}
