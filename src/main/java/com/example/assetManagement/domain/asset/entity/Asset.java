package com.example.assetManagement.domain.asset.entity;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import jakarta.persistence.*;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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
    private LocalDateTime purchasedAt;

    @Column(length = 500)
    private String memo;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column
    @Setter
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    @Setter
    private Boolean isDelete;

    @Builder
    public Asset(String assetNo, String name, Category category, String serialNo, LocalDateTime purchasedAt, String memo) {
        this.assetNo = assetNo;
        this.name = name;
        this.category = category;
        this.status = AssetStatus.IN_STOCK;
        this.serialNo = serialNo;
        this.purchasedAt = purchasedAt;
        this.memo = memo;
        this.isDelete = false;
    }

    public void updateInfo(String assetNo, String serialNo, String name, Category category, LocalDate purchasedAt, String memo) {
        this.assetNo = assetNo;
        this.serialNo = serialNo;
        this.name = name;
        this.category = category;
        this.purchasedAt = purchasedAt.atStartOfDay();
        this.memo = memo;
    }

    public void softDelete() {
        this.isDelete = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void assign() {
        this.status = AssetStatus.ASSIGNED;
    }

    public void returnAsset() {
        this.status = AssetStatus.IN_STOCK;
    }

    public void changeStatusToRepair() {
        this.status = AssetStatus.REPAIR;
    }
}
