package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerStockDetailsService {

    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;


    public StandardResponseMessage addCustomerStockDetails(StockDetailsPojo pojo) {
        try {
            branchStockDetailsRepository.updateStock(pojo.getStockId(), pojo.getIntakeNo(), "waiting", pojo.getPrice());
//            BranchStockDetailsEntity stockDetails = branchStockDetailsRepository.findByStockId(stockDetailsPojo.getStockId());
//            BranchStockDetailsEntity customerStockDetails = new BranchStockDetailsEntity();
//            customerStockDetails.setIntakeNo(stockDetailsPojo.getIntakeNo());
//            customerStockDetails.setStockId(stockDetailsPojo.getStockId());
//            customerStockDetails.setPrice(stockDetailsPojo.getPrice());
//            customerStockDetails.setStockName(stockDetails.getStockName());
//            customerStockDetailsRepository.save(customerStockDetails);
            List<BranchStockDetailsEntity> stockDetailsList = branchStockDetailsRepository.findAllByIntakeNo(pojo.getIntakeNo());
            return StandardResposneUtil.successResponse(stockDetailsList);
        } catch (Exception e) {
            log.error("Error Occurred in addCustomerStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }

    public StandardResponseMessage getCustomerStockList(String intakeNo) {
        try {
            List<BranchStockDetailsEntity> stockDetailsList = branchStockDetailsRepository.findAllByIntakeNo(intakeNo);
            if (Validators.hasData(stockDetailsList))
                return StandardResposneUtil.successResponse(stockDetailsList);
        } catch (Exception e) {
            log.error("Error Occurred in addCustomerStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
        return StandardResposneUtil.noDataMessage(Constant.NO_DATA_FOUND);
    }

    public StandardResponseMessage removeCustomerStockDetails(StockDetailsPojo stockDetailsPojo) {
        try {
            branchStockDetailsRepository.updateStockStatus("In Stock", stockDetailsPojo.getStockId());
            List<BranchStockDetailsEntity> list = branchStockDetailsRepository.findAllByIntakeNo(stockDetailsPojo.getIntakeNo());
            if (Validators.hasData(list))
                return StandardResposneUtil.successResponse(list);
            else
                return StandardResposneUtil.noDataMessage(Constant.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error Occurred in addCustomerStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }
}
