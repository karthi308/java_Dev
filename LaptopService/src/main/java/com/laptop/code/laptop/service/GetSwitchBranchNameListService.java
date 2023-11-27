package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.SwitchBranchPojoResponse;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GetSwitchBranchNameListService {


    @Autowired
    private RedisTemplate<String, String> redisCache;



}
