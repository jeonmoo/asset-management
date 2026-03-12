package com.example.assetManagement.domain.asset.dto.dashboard;

import com.example.assetManagement.domain.asset.enums.AssetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DashboardResponse {

    private long totalAssetCount;
    private List<StatusCount> statusCounts;
    private List<RecentAsset> RecentAssets;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class StatusCount {
        private AssetStatus status;
        private long count;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RecentAsset {
        private String assetNo;
        private String name;
        private String category;
        private String status;
        private LocalDateTime createdAt;
    }
}
