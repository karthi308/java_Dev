package com.io.tech.repository;

import com.io.tech.entity.CompanyDetailsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetailsEntity, String> {

    List<CompanyDetailsEntity> findByStatusOrderByCompanyNameAsc(String status);

    @Transactional
    @Modifying
    @Query("UPDATE CompanyDetailsEntity SET status = :status WHERE companyId = :companyId")
    void updateStatusById(String companyId, String status);

    CompanyDetailsEntity findByCompanyId(String companyId);

}
