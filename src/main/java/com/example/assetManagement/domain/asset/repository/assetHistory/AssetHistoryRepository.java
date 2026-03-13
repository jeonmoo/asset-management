package com.example.assetManagement.domain.asset.repository.assetHistory;

import com.example.assetManagement.domain.asset.entity.AssetHistory;
import com.example.assetManagement.domain.asset.enums.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {

    AssetHistory findByAssetIdAndReturnedAtIsNull(Long assetId);

    List<AssetHistory> findByAssetIdOrderByCreatedAtDesc(Long assetId);
}
