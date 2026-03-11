package com.example.assetManagement.domain.asset.repository;

import com.example.assetManagement.domain.asset.dto.AssetListResponse;
import com.example.assetManagement.domain.asset.dto.AssetSearchCondition;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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

@Repository
@RequiredArgsConstructor
public class AssetQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<AssetListResponse> findByAsset(AssetSearchCondition condition, Pageable pageable) {
        List<Asset> content = queryFactory.selectFrom(asset)
                .where(
//                        containName(condition.getName())
//                        , betweenFinalPrice(condition.get(), condition.getMaxPrice())
//                        , eqCategory(request.getCategoryId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
//                .orderBy(getOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(asset.count())
                .from(asset)
                .where(
//                        containName(condition.getName())
//                        , betweenFinalPrice(condition.get(), condition.getMaxPrice())
//                        , eqCategory(request.getCategoryId())
                );

//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
        return null;
    }

//    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
//        if (pageable.getSort().isEmpty()) {
//            return List.of(asset.createdAt.desc());
//        }
//
//        List<OrderSpecifier<?>> result = new ArrayList<>();
//
//        for (Sort.Order order : pageable.getSort()) {
//            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//
//            // 타입을 명시적으로 지정하여 OrderSpecifier 생성
//            OrderSpecifier<?> os = switch (order.getProperty()) {
//                case "finalPrice" -> new OrderSpecifier<>(direction, product.finalPrice);
//                case "productName" -> new OrderSpecifier<>(direction, product.productName);
//                case "createdAt" -> new OrderSpecifier<>(direction, product.createdAt);
//                default -> new OrderSpecifier<>(Order.DESC, product.createdAt);
//            };
//            result.add(os);
//        }
//
//        return result;
//    }

//    private BooleanExpression containName(String name) {
//        return StringUtils.hasText(name) ? product.productName.containsIgnoreCase(name) : null;
//    }
//
//    private BooleanExpression eqCategory(Long categoryId) {
//        return Objects.nonNull(categoryId) ? product.category.id.eq(categoryId) : null;
//    }
}
