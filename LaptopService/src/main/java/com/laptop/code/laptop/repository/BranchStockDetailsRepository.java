package com.laptop.code.laptop.repository;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.entity.VendorDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchStockDetailsRepository extends JpaRepository<BranchStockDetailsEntity, Integer> {
}
