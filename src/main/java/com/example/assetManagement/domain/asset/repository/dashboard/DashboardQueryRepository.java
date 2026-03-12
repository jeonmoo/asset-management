package com.example.assetManagement.domain.asset.repository.dashboard;

import com.example.assetManagement.domain.asset.dto.dashboard.DashboardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.assetManagement.domain.asset.entity.QAsset.asset;
import com.example.assetManagement.domain.asset.dto.QAssetListResponse;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DashboardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public long countTotalAssets() {
        Long count = queryFactory
                .select(asset.count())
                .from(asset)
                .where(asset.isDelete.isFalse())
                .fetchOne();

        return Optional.ofNullable(count).orElse(0L);
    }

    public List<DashboardResponse.StatusCount> findStatusCounts() {
        return queryFactory
                .select(Projections.constructor(DashboardResponse.StatusCount.class,
                        asset.status,
                        asset.count()
                ))
                .from(asset)
                .where(asset.isDelete.isFalse())
                .groupBy(asset.status)
                .fetch();
    }

    public List<DashboardResponse.RecentAsset> findRecentAssets() {
        return queryFactory
                .select(Projections.constructor(DashboardResponse.RecentAsset.class,
                        asset.assetNo,
                        asset.name,
                        asset.category.stringValue(),
                        asset.status.stringValue(),
                        asset.createdAt
                ))
                .from(asset)
                .where(asset.isDelete.isFalse())
                .orderBy(asset.createdAt.desc())
                .limit(5)
                .fetch();
    }
}
