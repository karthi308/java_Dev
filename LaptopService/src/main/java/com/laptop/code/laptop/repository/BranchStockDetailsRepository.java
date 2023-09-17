package com.laptop.code.laptop.repository;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchStockDetailsRepository extends JpaRepository<BranchStockDetailsEntity, Integer> {
    List<BranchStockDetailsEntity> findByBranchName(String branchName);
}
