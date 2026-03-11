package com.example.assetManagement.domain.asset.entity;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Asset {

    @Id
    private String assetNo;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status;

    @Column(unique = true)
    private String serialNo;

    @Column
    private LocalDate purchasedAt;

    @Column(length = 500)
    private String memo;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean isDelete;

    @Builder
    public Asset(String assetNo, String name, Category category, String serialNo, LocalDate purchasedAt, String memo) {
        this.assetNo = assetNo;
        this.name = name;
        this.category = category;
        this.status = AssetStatus.IN_STOCK;
        this.serialNo = serialNo;
        this.purchasedAt = purchasedAt;
        this.memo = memo;
        this.isDelete = false;
    }
}
