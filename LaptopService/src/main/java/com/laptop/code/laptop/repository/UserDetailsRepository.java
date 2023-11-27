package com.laptop.code.laptop.repository;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.apache.catalina.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, String> {

    @Modifying
    @Query(" update UserDetailsEntity  SET status = :status where userId = :userId")
    @Transactional
    void removeUser(String userId, String status);

    @Query("Select max(userId) FROM UserDetailsEntity ")
    String getMaxUserId();

//    @Query("FROM UserDetailsEntity where status = :status ")
    List<UserDetailsEntity> getUserDetailsByStatus(String status, Sort sort);
}
