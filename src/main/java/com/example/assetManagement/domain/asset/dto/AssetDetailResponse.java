package com.example.assetManagement.domain.asset.dto;

import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class AssetDetailResponse {

    private Long id;
    private String assetNo;
    private String name;
    private Category category;
    private AssetStatus status;
    private String assigneeName;
    private String serialNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchasedAt;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean isDelete;

    private List<History> histories;

    @Getter
    @Builder
    public static class History {
        private Long id;
        private Asset asset;
        private String assigneeName;
        private String assigneeEmail;
        private String department;
        private LocalDateTime assignedAt;
        private LocalDateTime returnedAt;
        private String note;
    }
}
