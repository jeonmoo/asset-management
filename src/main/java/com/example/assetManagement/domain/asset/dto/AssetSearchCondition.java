package com.example.assetManagement.domain.asset.dto;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetSearchCondition {

    private String q;
    private Category category;
    private AssetStatus status;

}
