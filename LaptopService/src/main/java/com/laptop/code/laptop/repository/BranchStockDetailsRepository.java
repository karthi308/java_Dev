package com.laptop.code.laptop.repository;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BranchStockDetailsRepository extends JpaRepository<BranchStockDetailsEntity, String> {
    List<BranchStockDetailsEntity> findByBranchName(String branchName);
    List<BranchStockDetailsEntity> findByBranchNameAndStatus(String branchName,String status);

    List<BranchStockDetailsEntity> findAllByIntakeNo(String stockId);


    @Query("FROM BranchStockDetailsEntity WHERE stockId = :stockId AND status = 'In Stock'")
    BranchStockDetailsEntity getStockDetailsById(String stockId);


    @Modifying
    @Query("UPDATE BranchStockDetailsEntity  SET status = :status, intakeNo = :intakeNo,price = :price  WHERE stockId = :stockId")
    @Transactional
    void updateStock(String stockId, String intakeNo, String status, long price);

    @Modifying
    @Query("UPDATE BranchStockDetailsEntity  SET status = :status WHERE stockId = :stockId")
    @Transactional
    void updateStockStatus(String stockId, String status);

    @Modifying
    @Query("UPDATE BranchStockDetailsEntity  SET branchName = :toBranchName, transferredFrom = :transferredFrom WHERE stockId = :stockId")
    @Transactional
    void transferStock(String stockId, String toBranchName, String transferredFrom);

}
