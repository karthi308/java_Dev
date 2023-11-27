package com.laptop.code.laptop.repository;


import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, String> {

//    @Query(" FROM CustomerDetailsEntity where intakeNo= :intakeNo AND branch = :branch")
//    List<CustomerDetailsEntity> findAllByIntakeNo(String intakeNo);
    List<CustomerDetailsEntity> findAllByIntakeNo(String intakeNo);

//    @Query(" FROM CustomerDetailsEntity where mobileNumber = :mobileNumber AND branch = :branch")
    List<CustomerDetailsEntity> findAllByMobileNumber(long mobileNo);

    @Query("Select max(year) FROM CustomerDetailsEntity ")
    String getMaxStockId(String year);

    //    @Query("SELECT COUNT(YD) FROM CustomerDetailsEntity where YD LIKE :date AND branch= :branch")
//    long countDistinctYDByDateBybranch(String date,String branch);
    @Query(" FROM CustomerDetailsEntity where status= :status")
    List<CustomerDetailsEntity> getNewStatus(String status);

    @Query(" FROM CustomerDetailsEntity where status= :status AND branch = :branch")
    List<CustomerDetailsEntity> getStatusByBranch(String branch, String status);

    @Modifying
    @Query(" update CustomerDetailsEntity  SET status = :stat where intakeNo = :intakeNo")
    @Transactional
    void updateStatus(String intakeNo, String stat);

    @Modifying
    @Query(" update CustomerDetailsEntity  SET status = 'Approved' ,price =:price where intakeNo = :intakeNo")
    @Transactional
    void updateApprovedStatus(String intakeNo, long price);

    @Modifying
    @Query(" update CustomerDetailsEntity  SET status = :stat, rejectedReason =:rejectedReason where intakeNo = :intakeno")
    @Transactional
    void updateRejectedStatus(String intakeno, String stat, String rejectedReason);


    @Modifying
    @Query("UPDATE CustomerDetailsEntity c SET c.status = :status WHERE c.intakeNo = :intakeNo")
    @Transactional
    void updateHandoutStatus(@Param("intakeNo") String intakeNo, @Param("status") String status);
//
//
//    @Query(" FROM CustomerDetailsEntity where mobileNumber= :mobileNo AND status !='Delivered'")
//    List<CustomerDetailsEntity> fetchHandInClientDetails(long mobileNo);
//
//    @Query(" FROM CustomerDetailsEntity where intakeNo= :intakeNo AND status !='Delivered'")
//    List<CustomerDetailsEntity> fetchHandInClientDetails(String intakeNo);
//
//    @Query(" FROM CustomerDetailsEntity where mobileNumber= :mobileNo ")
//    List<CustomerDetailsEntity> fetchAllClientDetails(long mobileNo);
//
//    @Query(" FROM CustomerDetailsEntity where intakeNo= :intakeNo ")
//    List<CustomerDetailsEntity> fetchAllClientDetails(String intakeNo);

    @Query(" FROM CustomerDetailsEntity where mobileNumber= :mobileNo AND branch= :branch")
    List<CustomerDetailsEntity> getByMobileNoByBranch(long mobileNo, String branch);

    @Modifying
    @Query(" update CustomerDetailsEntity  SET problemIdentified = :problemIdentified, price = :price ,status = 'Waiting for Confirmation'  where intakeNo = :intakeno")
    @Transactional
    void problemIdentified(String intakeno, String problemIdentified, long price);

//    @Query(" FROM CustomerDetailsEntity where price >0 AND YD LIKE :date ")
//    List<CustomerDetailsEntity> getPriceList(String date);

//    @Query(" FROM CustomerDetailsEntity where price >0 AND YD LIKE :date AND branch= :branch")
//    List<CustomerDetailsEntity> getPriceListByBranch(String date, String branch);

    @Query("FROM CustomerDetailsEntity where mobileNumber =:mobileNumber AND branch = :branchName")
    List<CustomerDetailsEntity> getStatusListBymobileNo(String mobileNumber, String branchName);

    @Query("FROM CustomerDetailsEntity where intakeNo =:intakeNO AND branch = :branchName")
    List<CustomerDetailsEntity> getStatusListIntakeNo(String intakeNO, String branchName);

    List<CustomerDetailsEntity> findByMobileNumber(long mobileNumber);

    CustomerDetailsEntity findByIntakeNo(String intakeNo);
}
