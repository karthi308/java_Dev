package com.laptop.code.laptop.repository;


import com.laptop.code.laptop.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Repository
public interface UserDetailsRepository  extends JpaRepository<UserDetailsEntity,String> {


    @Query("SELECT COUNT(YD) FROM UserDetailsEntity where YD LIKE :date AND branch= :branch")
    long countDistinctYDByDateBybranch(String date,String branch);
    @Query(" FROM UserDetailsEntity where status= :status")
    List<UserDetailsEntity> getNewStatus(String status);

    @Query(" FROM UserDetailsEntity where status= :status AND branch = :branch")
    List<UserDetailsEntity>  getNewStatusByBranch(String branch,String status);
    @Modifying
    @Query(" update UserDetailsEntity  SET status = :stat,updatedTime =:date where intakeNo = :intakeno")
    @Transactional
    void updateStatus(String intakeno,String stat,Date date);

    @Modifying
    @Query(" update UserDetailsEntity  SET status = :stat, rejectedReason =:rejectedReason,updatedTime =:date where intakeNo = :intakeno")
    @Transactional
    void updateRejectedStatus(String intakeno,String stat,String rejectedReason, Date date);

    @Modifying
    @Query(" update UserDetailsEntity  SET status = :status,updatedTime =:date where intakeNo = :intakeNo")
    @Transactional
    void updateHandoutStatus(String intakeNo,String status, Date date);

    @Query(" FROM UserDetailsEntity where mobileNumber= :mobileNo")
    List<UserDetailsEntity> getByMobileNo(long mobileNo);

    @Query(" FROM UserDetailsEntity where intakeNo= :intakeNo")
    List<UserDetailsEntity> getByIntakeNo(String intakeNo);

    @Query(" FROM UserDetailsEntity where mobileNumber= :mobileNo AND branch= :branch")
    List<UserDetailsEntity> getByMobileNoByBranch(long mobileNo,String branch);
    @Modifying
    @Query(" update UserDetailsEntity  SET problemIdentified = :problemIdentified, price = :price ,status= :status ,updatedTime = :date where intakeNo = :intakeno")
    @Transactional
    void problemIdentified( String intakeno, String problemIdentified,long price,String status,Date date);

    @Query(" FROM UserDetailsEntity where price >0 AND YD LIKE :date ")
    List<UserDetailsEntity> getPriceList(String date);

    @Query(" FROM UserDetailsEntity where price >0 AND YD LIKE :date AND branch= :branch")
    List<UserDetailsEntity> getPriceListByBranch(String date,String branch);

    @Query("FROM UserDetailsEntity where mobileNumber =:mobileNumber AND branch = :branchName")
    List<UserDetailsEntity> getStatusListBymobileNo(String mobileNumber,String branchName);

    @Query("FROM UserDetailsEntity where intakeNo =:intakeNO AND branch = :branchName")
    List<UserDetailsEntity> getStatusListIntakeNo(String intakeNO,String branchName);

}
