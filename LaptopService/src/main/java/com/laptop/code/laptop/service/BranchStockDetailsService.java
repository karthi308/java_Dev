package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.util.CacheUtil;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BranchStockDetailsService {
    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;
    @Autowired
    CacheUtil cache;


    public StandardResponseMessage getAllStockDetails(HttpServletRequest request) {
        try {
            String branch = cache.getValue(request.getHeader(Constant.USER_ID)+ Constant.BRANCH);
            List<BranchStockDetailsEntity> stockDetails = branchStockDetailsRepository.findByBranchName(branch);
            if (Validators.hasData(stockDetails))
                return StandardResposneUtil.successResponse(stockDetails);
            else
                return StandardResposneUtil.noDataResponse();
        } catch (Exception e) {
            log.error("Error Occurred in getBranchAllStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    } public StandardResponseMessage getStockDetailsByStatus(HttpServletRequest request,String status) {
        try {
            String branch = cache.getValue(request.getHeader(Constant.USER_ID)+ Constant.BRANCH);
            List<BranchStockDetailsEntity> stockDetails = branchStockDetailsRepository.findByBranchNameAndStatus(branch,status);
            if (Validators.hasData(stockDetails))
                return StandardResposneUtil.successResponse(stockDetails);
            else
                return StandardResposneUtil.noDataMessage(Constant.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error Occurred in getBranchAllStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }

    public StandardResponseMessage getStockDetails(String stockId) {
        try {
            BranchStockDetailsEntity stockDetails = branchStockDetailsRepository.getStockDetailsById(stockId);
            if (Validators.hasData(stockDetails))
                return StandardResposneUtil.successMessage(stockDetails.getStockName() + ", " + stockDetails.getWarranty() + " Warranty");
        } catch (Exception e) {
            log.error("Error Occurred in getBranchStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
        return StandardResposneUtil.noDataMessage("Stock not Available");
    }

    public StandardResponseMessage transferStock(HttpServletRequest request, StockDetailsPojo pojo) {
        try{
            if(Validators.hasData(pojo.getStockId()) && Validators.hasData(pojo.getBranchName())) {
                String branchName = cache.getValue(request.getHeader(Constant.USER_ID)+ Constant.BRANCH);
                branchStockDetailsRepository.transferStock(pojo.getStockId(), pojo.getBranchName(),branchName);
                return StandardResposneUtil.successMessage("Succesfully stock Transfered");
            }
            else
                return StandardResposneUtil.badRequestResponse();
        }
        catch (Exception e){
            log.error("Error Occurred in transferStock Service : "+e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }


}

