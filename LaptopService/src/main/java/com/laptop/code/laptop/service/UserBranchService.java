package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserBranchService {
    @Autowired
    UserDetailsRepository newUserRepository;

    @Autowired
    private RedisTemplate<String, String> redisCache;
    private static Logger logger = LoggerFactory.getLogger(UserBranchService.class);

    public StandardResponseMessage getSwitchedBranchName(HttpServletRequest request){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            String userId = request.getHeader(Constant.USER_ID);
            Optional<UserDetailsEntity> userList = newUserRepository.findById(userId);
            if (Validators.hasData(userList)) {
//                standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList(userList.get().getSwitchBranchName()));
                //redis get switch branch name
            } else {
                standardResponseMessage= StandardResposneUtil.notFound();
            }
        }
        catch (Exception e){
            logger.error("Error Occurred in getSwitchBranchName Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
}
