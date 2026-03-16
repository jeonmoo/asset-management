package com.example.assetManagement.domain.asset.repository.assetHistory;

import com.example.assetManagement.domain.asset.entity.AssetHistory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {


    AssetHistory findByAssetIdAndReturnedAtIsNull(Long assetId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    AssetHistory findWithLockByAssetIdAndReturnedAtIsNull(Long assetId);

    List<AssetHistory> findByAssetIdOrderByAssignedAtDesc(Long assetId);
}
