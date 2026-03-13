package com.example.assetManagement.domain.asset.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AssetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private String assigneeName;

    private String assigneeEmail;

    private String department;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    private LocalDateTime returnedAt;

    @Column(length = 500)
    private String note;

    @Builder
    public AssetHistory(Asset asset, String assigneeName, String assigneeEmail, String department, String note) {
        this.asset = asset;
        this.assigneeName = assigneeName;
        this.assigneeEmail = assigneeEmail;
        this.department = department;
        this.assignedAt = LocalDateTime.now();
        this.note = note;
    }
}
