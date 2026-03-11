package com.example.assetManagement.domain.asset.repository;

import com.example.assetManagement.domain.asset.dto.AssetListResponse;
import com.example.assetManagement.domain.asset.dto.AssetSearchCondition;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.assetManagement.domain.asset.entity.QAsset.asset;
import com.example.assetManagement.domain.asset.dto.QAssetListResponse;

@Repository
@RequiredArgsConstructor
public class AssetQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<AssetListResponse> findByAsset(AssetSearchCondition condition, Pageable pageable) {
        List<AssetListResponse> content = queryFactory
                .select(new QAssetListResponse(
                        asset.assetNo,
                        asset.name,
                        asset.category.stringValue(), // Enum -> String 변환
                        asset.status.stringValue(),   // Enum -> String 변환
                        asset.purchasedAt
                ))
                .from(asset)
                .where(
                        containName(condition.getQ())
                        , eqCategory(condition.getCategory())
                        , eqStatus(condition.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(asset.count())
                .from(asset)
                .where(
                        containName(condition.getQ())
                        , eqCategory(condition.getCategory())
                        , eqStatus(condition.getStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return List.of(asset.createdAt.desc());
        }

        List<OrderSpecifier<?>> result = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            // 타입을 명시적으로 지정하여 OrderSpecifier 생성
            OrderSpecifier<?> os = switch (order.getProperty()) {
                case "purchasedAt" -> new OrderSpecifier<>(direction, asset.purchasedAt);
                case "name" -> new OrderSpecifier<>(direction, asset.name);
                default -> new OrderSpecifier<>(Order.DESC, asset.createdAt);
            };
            result.add(os);
        }

        return result;
    }

    private BooleanExpression containName(String q) {
        if (!StringUtils.hasText(q)) {
            return null;
        }

        return asset.assetNo.containsIgnoreCase(q)
                .or(asset.name.containsIgnoreCase(q))
                .or(asset.serialNo.containsIgnoreCase(q));
    }

    private BooleanExpression eqCategory(Category category) {
        return Objects.nonNull(category) ? asset.category.eq(category) : null;
    }

    private BooleanExpression eqStatus(AssetStatus status) {
        return Objects.nonNull(status) ? asset.status.eq(status) : null;
    }
}
