package com.example.assetManagement.domain.asset.repository;

import com.example.assetManagement.domain.asset.entity.Asset;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Asset> findWithLockById(Long id);

    Boolean existsByAssetNo(String assetNo);

    Boolean existsByAssetNoAndIdNot(String assetNo, Long id);
}
