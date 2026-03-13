package com.example.assetManagement.domain.asset.repository.assetHistory;

import com.example.assetManagement.domain.asset.entity.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
}
