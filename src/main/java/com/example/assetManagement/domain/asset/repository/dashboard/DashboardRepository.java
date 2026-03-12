package com.example.assetManagement.domain.asset.repository.dashboard;

import com.example.assetManagement.domain.asset.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Asset, String> {

}
