package com.example.assetManagement.domain.asset.dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.print.Pageable;

@Getter
@Setter
public class AssetSearchCondition {

    private String assetNo;
    private String name;
    private String serialNo;

}
