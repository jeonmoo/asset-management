package com.example.assetManagement.domain.asset.dto;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AssetModifyRequest {

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
}
