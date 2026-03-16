package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.entity.AssetHistory;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import com.example.assetManagement.domain.asset.repository.assetHistory.AssetHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class AssetServiceTest {

    @Autowired
    AssetService assetService;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssetHistoryRepository assetHistoryRepository;


    @Test
    @DisplayName("자산 생성 테스트")
    void createAssetTest() {
        // given
        String assetNo = "test-1";
        String name = "labtop-1";
        Category category = Category.LAPTOP;
        String serialNo = "0000-0000-0000-0000";
        LocalDate purchasedAt = LocalDate.now();
        String memo = "테스트 저장";

        AssetCreateRequest request = new AssetCreateRequest();
        request.setAssetNo(assetNo);
        request.setName(name);
        request.setCategory(category);
        request.setSerialNo(serialNo);
        request.setPurchasedAt(purchasedAt);
        request.setMemo(memo);

        // when
        Asset savedAsset =  assetService.createAsset(request);

        // then
        assertAll(
                () -> assertThat(savedAsset.getAssetNo()).isEqualTo(request.getAssetNo()),
                () -> assertThat(savedAsset.getName()).isEqualTo(request.getName()),
                () -> assertThat(savedAsset.getCategory()).isEqualTo(request.getCategory()),
                () -> assertThat(savedAsset.getStatus()).isEqualTo(AssetStatus.IN_STOCK),
                () -> assertThat(savedAsset.getSerialNo()).isEqualTo(serialNo),
                () -> assertThat(savedAsset.getMemo()).isEqualTo(memo)
        );
    }

    @Test
    @DisplayName("자산 할당 테스트")
    void assignAssetTest() {
        // given
        String assetNo = "test-1";
        String name = "labtop-1";
        Category category = Category.LAPTOP;
        String serialNo = "0000-0000-0000-0000";
        LocalDate purchasedAt = LocalDate.now();
        String memo = "테스트 저장";

        Asset asset = Asset.builder()
                .assetNo(assetNo)
                .name(name)
                .category(category)
                .serialNo(serialNo)
                .purchasedAt(purchasedAt.atStartOfDay())
                .memo(memo)
                .build();
        Asset savedAsset = assetRepository.save(asset);

        String assigneeName = "김사원";
        String assigneeEmail = "test@test.com";
        String department = "영업부";
        String note = "기스 많았음";
        AssetAssignRequest request = new AssetAssignRequest();
        request.setAssigneeName(assigneeName);
        request.setAssigneeEmail(assigneeEmail);
        request.setDepartment(department);
        request.setNote(note);

        // when
        Asset assignedAsset = assetService.assignAsset(savedAsset.getId(), request);

        // then
        AssetHistory history = assetHistoryRepository.findByAssetIdAndReturnedAtIsNull(savedAsset.getId());
        assertAll(
                () -> assertThat(assignedAsset.ge)
        );
    }
}