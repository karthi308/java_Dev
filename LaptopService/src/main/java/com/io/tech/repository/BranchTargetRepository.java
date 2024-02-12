package com.io.tech.repository;

import com.io.tech.entity.BranchTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BranchTargetRepository extends JpaRepository<BranchTargetEntity, String> {


}
