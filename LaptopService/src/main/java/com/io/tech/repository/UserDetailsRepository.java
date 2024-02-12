package com.io.tech.repository;

import com.io.tech.entity.UserDetailsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, UserDetailsId> {
    @Query("Select max(id.userId) FROM UserDetailsEntity where id.companyId=:companyId")
    String getMaxIdUserId(String companyId);

    List<UserDetailsEntity> findByIdCompanyIdAndStatusOrderByIdUserId(String companyId, String status);

    List<UserDetailsEntity> findByIdCompanyIdOrderByIdUserId(String companyId);
    UserDetailsEntity findByIdCompanyIdAndIdUserId(String companyId,String userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserDetailsEntity SET status = :status WHERE id.companyId = :companyId and id.userId = :userId")
    void updateStatusById(String companyId, String userId, String status);

    @Transactional
    @Modifying
    @Query("UPDATE UserDetailsEntity SET userKey = '' WHERE id.companyId = :companyId and id.userId = :userId")
    void removeUserKey(String companyId, String userId);
    UserDetailsEntity findByIdCompanyIdAndIdUserIdAndUserKey(String companyId,String userId,String userKey);
}


