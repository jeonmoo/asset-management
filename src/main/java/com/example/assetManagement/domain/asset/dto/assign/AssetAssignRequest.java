package com.example.assetManagement.domain.asset.dto.assign;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssetAssignRequest {
    private Long assetId;
    private String assigneeName;
    private String assigneeEmail;
    private String department;
    private String note;
}
