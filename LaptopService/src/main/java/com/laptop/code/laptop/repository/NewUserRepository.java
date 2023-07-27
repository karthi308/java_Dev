package com.laptop.code.laptop.repository;

import com.laptop.code.laptop.entity.NewUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface NewUserRepository  extends JpaRepository<NewUserEntity,String> {

}
