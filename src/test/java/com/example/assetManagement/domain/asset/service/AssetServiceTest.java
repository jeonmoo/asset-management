package com.example.assetManagement.domain.asset.service;

import com.example.assetManagement.domain.asset.dto.AssetCreateRequest;
import com.example.assetManagement.domain.asset.dto.assign.AssetAssignRequest;
import com.example.assetManagement.domain.asset.entity.Asset;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import com.example.assetManagement.domain.asset.enums.Category;
import com.example.assetManagement.domain.asset.mapper.AssetMapper;
import com.example.assetManagement.domain.asset.repository.AssetQueryRepository;
import com.example.assetManagement.domain.asset.repository.AssetRepository;
import com.example.assetManagement.domain.asset.repository.assetHistory.AssetHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    AssetSupportService assetSupportService;
    @Mock
    AssetRepository assetRepository;
    @Mock
    AssetQueryRepository assetQueryRepository;
    @Mock
    AssetHistoryRepository assetHistoryRepository;
    @Mock
    AssetMapper assetMapper;

    @InjectMocks
    AssetService assetService;

    @Captor
    private ArgumentCaptor<Asset> assetCaptor;

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

        Asset mockAsset = Asset.builder()
                .assetNo(assetNo)
                .name(name)
                .category(category)
                .serialNo(serialNo)
                .purchasedAt(purchasedAt.atStartOfDay())
                .memo(memo)
                .build();

        given(assetMapper.toAsset(request)).willReturn(mockAsset);

        // when
        assetService.createAsset(request);
        verify(assetRepository).save(assetCaptor.capture());

        // then
        Asset savedAsset = assetCaptor.getValue();
        assertThat(savedAsset)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "isDelete", "deletedAt")
                .isEqualTo(mockAsset);
    }

    @Test
    @DisplayName("자산 할당 테스트")
    void assignAssetTest() {
        // given
        Long assetId = 1L;
        String assetNo = "test-1";
        String name = "labtop-1";
        Category category = Category.LAPTOP;
        String serialNo = "0000-0000-0000-0000";
        LocalDate purchasedAt = LocalDate.now();
        String memo = "테스트 저장";

        Asset mockAsset = Asset.builder()
                .assetNo(assetNo)
                .name(name)
                .category(category)
                .serialNo(serialNo)
                .purchasedAt(purchasedAt.atStartOfDay())
                .memo(memo)
                .build();

        given(assetRepository.save(any(Asset.class))).willReturn(mockAsset);
        Asset savedAsset = assetRepository.save(mockAsset);

        given(assetRepository.findWithLockById(assetId)).willReturn(Optional.of(mockAsset));

        String assigneeName = "김사원";
        String assigneeEmail = "test@test.com";
        String department = "경영지원부";
        String note = "노트북 기스 많았음";
        AssetAssignRequest request = new AssetAssignRequest();
        request.setAssigneeName(assigneeName);
        request.setAssigneeEmail(assigneeEmail);
        request.setDepartment(department);
        request.setNote(note);

        // when
        assetService.assignAsset(assetId, request);

        // then
        assertThat(mockAsset.getStatus()).isEqualTo(AssetStatus.ASSIGNED);
    }

}
