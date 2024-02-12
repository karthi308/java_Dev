package com.io.tech.repository;

import com.io.tech.entity.BranchStockDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface BranchStockDetailsRepository extends JpaRepository<BranchStockDetailsEntity, StockDetailsId> {

    List<BranchStockDetailsEntity> findByIdCompanyIdAndBranchNameOrderByIdStockIdAsc(String companyId, String branchName);
    BranchStockDetailsEntity findByIdCompanyIdAndIdStockId(String companyId, String stockId);

    List<BranchStockDetailsEntity> findByIdCompanyIdAndBranchNameAndStatus(String companyId, String branchName, String status);

    List<BranchStockDetailsEntity> findByIdCompanyIdAndIntakeNoOrderByIdStockIdAsc(String companyId, String intakeNo);


}
