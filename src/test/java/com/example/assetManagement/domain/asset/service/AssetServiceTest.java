package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.AssetModifyRequest;
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
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.within;
import java.time.temporal.ChronoUnit;

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
    @DisplayName("자산 수정 테스트")
    void modifyAssetTest() {
        // given
        String assetNo = "test-1";

        Asset asset = Asset.builder()
                .assetNo(assetNo)
                .name("김사원")
                .category(Category.LAPTOP)
                .serialNo("0000-0000-0000-0000")
                .purchasedAt(LocalDateTime.now().minusDays(1))
                .memo("이전 메모")
                .build();
        Asset savedAsset = assetRepository.save(asset);

        String newAssertNo = "2026-03-16-0001";
        Category newCategory = Category.ETC;
        String newSerialNo = "0000-0000-0000-0000";
        LocalDate newPurchasedAt = LocalDate.now();
        String newMemo = "새로운 메모";

        AssetModifyRequest request = new AssetModifyRequest();
        request.setAssetNo(newAssertNo);
        request.setCategory(newCategory);
        request.setSerialNo(newSerialNo);
        request.setPurchasedAt(newPurchasedAt);
        request.setMemo(newMemo);

        // when
        assetService.modifyAsset(savedAsset.getId(), request);

        // then
        Asset selectedAsset = assetRepository.findById(savedAsset.getId()).orElseThrow();
        assertAll(
                () -> assertThat(selectedAsset.getAssetNo()).isEqualTo(newAssertNo),
                () -> assertThat(selectedAsset.getCategory()).isEqualTo(newCategory),
                () -> assertThat(selectedAsset.getSerialNo()).isEqualTo(newSerialNo),
                () -> assertThat(selectedAsset.getPurchasedAt().toLocalDate()).isEqualTo(newPurchasedAt),
                () -> assertThat(selectedAsset.getMemo()).isEqualTo(newMemo)
        );
    }

    @Test
    @DisplayName("자산 삭제 테스트")
    void deleteAssetTest() {
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

        // when
        assetService.deleteAsset(savedAsset.getId());

        // then
        Asset selectedAsset = assetRepository.findById(savedAsset.getId()).orElseThrow();
        assertAll(
                () -> assertThat(selectedAsset.getIsDelete()).isTrue(),
                () -> assertThat(selectedAsset.getDeletedAt()).isNotNull()
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
                () -> assertThat(assignedAsset.getStatus()).isEqualTo(AssetStatus.ASSIGNED),
                () -> assertThat(history.getAssignedAt()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS)),
                () -> assertThat(history.getAssigneeName()).isEqualTo(assigneeName),
                () -> assertThat(history.getAssigneeEmail()).isEqualTo(assigneeEmail),
                () -> assertThat(history.getDepartment()).isEqualTo(department),
                () -> assertThat(history.getNote()).isEqualTo(note)
        );
    }

    @Test
    @DisplayName("자산 회수 테스트")
    void returnAssetTest() {
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
        asset.assign();
        Asset savedAsset = assetRepository.save(asset);

        AssetHistory history = AssetHistory.builder()
                .asset(asset)
                .assigneeName("김사원")
                .assigneeEmail("test@test.com")
                .department("영업부")
                .note("기스 많음")
                .build();

        assetHistoryRepository.save(history);

        // when
        assetService.returnAsset(savedAsset.getId());

        // then
        Asset selectedAsset = assetRepository.findById(savedAsset.getId()).orElseThrow();
        AssetHistory selectedHistory = assetHistoryRepository.findByAssetIdOrderByAssignedAtDesc(selectedAsset.getId()).get(0);
        assertAll(
                () -> assertThat(selectedAsset.getStatus()).isEqualTo(AssetStatus.IN_STOCK),
                () -> assertThat(selectedHistory.getReturnedAt()).isNotNull()
        );
    }

    @Test
    @DisplayName("자산 수리요청 테스트 - 할당된 자산은 회수 처리 된다.")
    void repairAssetTest() {
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
        asset.assign();
        Asset savedAsset = assetRepository.save(asset);

        AssetHistory history = AssetHistory.builder()
                .asset(asset)
                .assigneeName("김사원")
                .assigneeEmail("test@test.com")
                .department("영업부")
                .note("기스 많음")
                .build();

        assetHistoryRepository.save(history);

        String assigneeName = "김사원";
        String assigneeEmail = "test@test.com";
        String department = "영업부";
        String note = "";
        AssetAssignRequest request = new AssetAssignRequest();
        request.setAssigneeName(assigneeName);
        request.setAssigneeEmail(assigneeEmail);
        request.setDepartment(department);
        request.setNote(note);

        // when
        assetService.repairAsset(savedAsset.getId());

        // then
        Asset selectedAsset = assetRepository.findById(savedAsset.getId()).orElseThrow();
        AssetHistory selectedHistory = assetHistoryRepository.findByAssetIdOrderByAssignedAtDesc(selectedAsset.getId()).get(0);

        assertAll(
                () -> assertThat(selectedAsset.getStatus()).isEqualTo(AssetStatus.REPAIR),
                () -> assertThat(selectedHistory.getReturnedAt()).isNotNull()
        );
    }

    @Test
    @DisplayName("자산 수리 완료 테스트")
    void completeRepairAssetTest() {
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
        asset.changeStatusToRepair();
        Asset savedAsset = assetRepository.save(asset);

        // when
        Asset processedAsset = assetService.completeRepairAsset(savedAsset.getId());

        // then
        assertThat(processedAsset.getStatus()).isEqualTo(AssetStatus.IN_STOCK);
    }
}