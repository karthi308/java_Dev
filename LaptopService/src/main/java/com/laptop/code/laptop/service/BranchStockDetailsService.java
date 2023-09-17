package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BranchStockDetailsService {
    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;

    public StandardResponseMessage getBranchStockDetails() {
        try {
            List<BranchStockDetailsEntity> stockDetails = branchStockDetailsRepository.findByBranchName("IOTECR");
            if (Validators.hasData(stockDetails))
                return StandardResposneUtil.successResponse(stockDetails);
            else
                return StandardResposneUtil.noDataResponse();
        } catch (Exception e) {
            log.error("Error Occurred in getBranchStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }

    public StandardResponseMessage transferStock(StockDetailsPojo stockDetailsPojo) {
        try {
            System.out.println("res ");
            BranchStockDetailsEntity stockDetails = branchStockDetailsRepository.getById(stockDetailsPojo.getStockId());
            if (Validators.hasData(stockDetails.getStockId())) {
                BranchStockDetailsEntity branchStockDetailsEntity =new BranchStockDetailsEntity();
                branchStockDetailsEntity.setStockId(stockDetails.getStockId());
                branchStockDetailsEntity.setStockName(stockDetails.getStockName());
                branchStockDetailsEntity.setBranchName(stockDetailsPojo.getTransferTo());
                branchStockDetailsEntity.setPrice(stockDetails.getPrice());
                branchStockDetailsEntity.setWarranty(stockDetails.getWarranty());
                branchStockDetailsEntity.setTransferredFrom(stockDetails.getTransferredFrom());
                branchStockDetailsEntity.setStatus(stockDetails.getStatus());
                branchStockDetailsRepository.save(branchStockDetailsEntity);
                return StandardResposneUtil.successResponse(Collections.singletonList("Successfully Stock Transferred"));
            } else
                return StandardResposneUtil.noDataResponse();
        } catch (Exception e) {
            log.error("Error Occurred in transferStock Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }


}

