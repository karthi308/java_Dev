package com.laptop.code.laptop.repository;

import com.laptop.code.laptop.entity.VendorDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorDetailsRepository extends JpaRepository<VendorDetailsEntity, String> {
    @Query("Select max(year) FROM BranchStockDetailsEntity ")
    String getMaxStockId();


    List<VendorDetailsEntity> findByStatus(String status);
}
