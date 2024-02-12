package com.io.tech.repository;

import com.io.tech.entity.BranchDetailsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BranchDetailsRepository extends JpaRepository<BranchDetailsEntity, BranchDetailsId> {

    List<BranchDetailsEntity> findByIdCompanyId(String companyId);

    @Query("SELECT id.branchName FROM BranchDetailsEntity  WHERE id.companyId = :companyId and status = 'Active'")
    List<String> findAllBranchNames(String companyId);

    List<BranchDetailsEntity> findByIdCompanyIdAndStatus(String companyId, String status);


    BranchDetailsEntity findByIdCompanyIdAndIdBranchName(String companyId, String branchName);

    @Transactional
    @Modifying
    @Query("UPDATE BranchDetailsEntity SET status = :status WHERE id.companyId = :companyId AND id.branchName = :branchName")
    void updateStatusByCompanyId(String companyId, String branchName, String status);

}
