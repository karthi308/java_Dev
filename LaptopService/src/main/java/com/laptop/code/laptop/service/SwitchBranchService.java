package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.AddUserPojo;
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
public class SwitchBranchService {
    @Autowired
    private RedisTemplate<String, String> redisCache;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    public StandardResponseMessage switchBranch(HttpServletRequest request, AddUserPojo addUserPojo) {
        try {
            String userId = request.getHeader(Constant.USER_ID);
            redisCache.opsForValue().set(userId + "branch", addUserPojo.getBranchName());
//            SwitchBranchPojoResponse switchBranchPojoResponse = new SwitchBranchPojoResponse();
//            switchBranchPojoResponse.setCurrentBranchName(redisCache.opsForValue().get(userId+"branch"));
//            List<SwitchBranchPojoResponse> responseList = new ArrayList<>();
//            responseList.add(switchBranchPojoResponse);
            return StandardResposneUtil.successMessage("Successfully Switched to " + addUserPojo.getBranchName());
        } catch (Exception e) {
            log.error("Error Occurred in switchBranch Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }

    public StandardResponseMessage getSwitchBranchList(HttpServletRequest request) {
        try {
            List<UserDetailsEntity> userDetailsList = userDetailsRepository.findAll();
            if (Validators.hasData(userDetailsList)) {
                SwitchBranchPojoResponse switchBranchPojoResponse = new SwitchBranchPojoResponse();
                List<SwitchBranchPojoResponse> responsesList = new ArrayList<>();
                String userId = request.getHeader(Constant.USER_ID);
                switchBranchPojoResponse.setCurrentBranchName(redisCache.opsForValue().get(userId + "branch"));
                List<String> branchNameList = new ArrayList<>();
                for (UserDetailsEntity userDetails : userDetailsList) {
                    String branchName = userDetails.getBranchName();
                    if (!branchNameList.contains(branchName))
                        branchNameList.add(branchName);
                }
                switchBranchPojoResponse.setBranchList(branchNameList);
                responsesList.add(switchBranchPojoResponse);
                return StandardResposneUtil.successResponse(responsesList);
            } else
                return StandardResposneUtil.noDataResponse();
        } catch (Exception e) {
            log.error("Error Occurred in getSwitchBranchList Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }
}
