package com.io.tech.repository;

import com.io.tech.entity.StockDetailsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StockDetailsRepository extends JpaRepository<StockDetailsEntity, StockDetailsId> {
    StockDetailsEntity findTopByIdCompanyIdOrderByYearDesc(String companyId);

    List<StockDetailsEntity> findByIdCompanyIdOrderByIdStockIdAsc(String companyId);

    StockDetailsEntity findByIdCompanyIdAndIdStockId(String companyId, String stockId);

    List<StockDetailsEntity> findByIdCompanyIdAndStatus(String companyId, String status);

    @Transactional
    @Modifying
    @Query("UPDATE StockDetailsEntity SET status = :status where id.stockId = :stockId AND id.companyId = :companyId")
    void updateStatusByIdCompanyIdAndIdStockId(String companyId, String stockId, String status);


}
