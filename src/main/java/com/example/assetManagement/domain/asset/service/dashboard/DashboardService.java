package com.example.assetManagement.domain.asset.service.dashboard;

import com.example.assetManagement.domain.asset.dto.dashboard.DashboardResponse;
import com.example.assetManagement.domain.asset.repository.AssetQueryRepository;
import com.example.assetManagement.domain.asset.repository.dashboard.DashboardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardQueryRepository dashboardQueryRepository;

    public DashboardResponse getDashboardInfo() {
        long totalCount = dashboardQueryRepository.countTotalAssets();
        List<DashboardResponse.StatusCount> statusCounts = dashboardQueryRepository.findStatusCounts();
        List<DashboardResponse.RecentAsset> recentAssets = dashboardQueryRepository.findRecentAssets();

        return DashboardResponse.builder()
                .totalAssetCount(totalCount)
                .statusCounts(statusCounts)
                .RecentAssets(recentAssets)
                .build();
    }
}
