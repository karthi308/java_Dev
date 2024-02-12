package com.io.tech.repository;

import com.io.tech.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, ProductId> {
    ProductEntity findTopByIdCompanyIdAndBranchNameOrderByYearDesc(String companyId, String branchName);

    List<ProductEntity> findByIdCompanyIdAndBranchNameOrderByYearAsc(String companyId, String branchName);

    ProductEntity findByIdCompanyIdAndIdIntakeNoAndBranchName(String companyId, String intakeNo, String branchName);
    @Query("FROM ProductEntity WHERE id.companyId = :companyId AND mobileNumber = :mobileNo AND branchName= :branchName AND status != 'Delivered' AND status != 'Returned' AND status = :status")
    List<ProductEntity> getProductDetailsByMobileNo(String companyId, String mobileNo, String branchName,String status);

    @Query("FROM ProductEntity WHERE id.companyId = :companyId AND id.intakeNo = :intakeNo AND branchName= :branchName AND status != 'Delivered' AND status != 'Returned' AND status = :status")
    List<ProductEntity> getProductDetailsByIntakeNo(String companyId, String intakeNo, String branchName,String status);

    ProductEntity findTopByIdCompanyIdAndMobileNumberAndBranchNameOrderByIdIntakeNoDesc(String companyId, long searchNo, String branchName);

    List<ProductEntity> findByIdCompanyIdAndBranchNameAndStatusOrderByYearAsc(String companyId, String branchName, String status);

    @Modifying
    @Query("DELETE FROM ProductEntity WHERE id.companyId = :companyId AND id.intakeNo = :intakeNo AND branchName = :branchName")
    @Transactional
    void removeIntake(@Param("companyId") String companyId, @Param("intakeNo") String intakeNo, @Param("branchName") String branchName);
}


