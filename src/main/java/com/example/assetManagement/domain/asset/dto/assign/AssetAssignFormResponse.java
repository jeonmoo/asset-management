package com.example.assetManagement.domain.asset.dto.assign;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AssetAssignFormResponse {

    private Long id;
    private String assetNo;
    private String name;
    private Category category;
    private AssetStatus status;
    private String serialNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean isDelete;
    private String assigneeName;
    private String assigneeEmail;
    private String department;
    private String note;
}
