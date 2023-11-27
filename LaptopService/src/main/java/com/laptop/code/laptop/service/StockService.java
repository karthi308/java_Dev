package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.entity.VendorDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.repository.VendorDetailsRepository;
import com.laptop.code.laptop.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StockService {
    @Autowired
    VendorDetailsRepository vendorDetailsRepo;

    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;
    @Autowired
    CacheUtil cache;

    public StandardResponseMessage addStockDetails(StockDetailsPojo pojo) {
        StandardResponseMessage standardResponseMessage = new StandardResponseMessage();
        try {
            String yearAndMonth = DateFormatter.getYearAndMonth();
            String existingStockId = vendorDetailsRepo.getMaxStockId();
            String numberPart = "";
            String startPoint = "";
            if (Validators.hasData(existingStockId)) {
                numberPart = existingStockId.replaceAll("[^0-9]", "");
                if (!yearAndMonth.equals(numberPart.substring(0, 4)))
                    numberPart = yearAndMonth + "0000";

            } else
                numberPart = yearAndMonth + "0000";
            if (pojo.getStockStatus().equals("PRODUCTION"))
                startPoint = "IOTPR";
            else if (pojo.getStockStatus().equals("GODOWN")) {
                startPoint = "IOTGD";
            }
            VendorDetailsEntity vendorDetailsEntity = new VendorDetailsEntity();
            vendorDetailsEntity.setStockId(startPoint + (Integer.parseInt(numberPart) + 1));
            vendorDetailsEntity.setStockName(pojo.getStockName());
            vendorDetailsEntity.setVendorName(pojo.getVendorName());
            vendorDetailsEntity.setPrice(pojo.getPrice());
            vendorDetailsEntity.setWarranty(pojo.getWarranty());
            vendorDetailsEntity.setVendorLocation(pojo.getVendorLocation());
            vendorDetailsRepo.save(vendorDetailsEntity);
            List<VendorDetailsEntity> vendorDetailsEntityList = new ArrayList<>();
            vendorDetailsEntityList.add(vendorDetailsEntity);
            standardResponseMessage = StandardResposneUtil.successResponse(vendorDetailsEntityList);
        } catch (Exception e) {
            log.error("Error in addStockDetails Service : " + e.getMessage());
            standardResponseMessage = StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getStockDetails(String userId, String status) {
        try {
            String userDetails = cache.getValue(userId + "details");
            UserDetailsEntity userDetailsList = CommonUtil.getASJsonArray(userDetails);
            if (Validators.hasData(userDetailsList) && userDetailsList.isVendorStockAccess()) {
                List<VendorDetailsEntity> list = vendorDetailsRepo.findByStatus(status);
                if (Validators.hasData(list))
                    return StandardResposneUtil.successResponse(list);
                else
                    return StandardResposneUtil.noDataMessage(Constant.NO_DATA_FOUND);
            } else
                return StandardResposneUtil.noDataMessage("Access Denied");
        } catch (Exception e) {
            log.error("Error Occurred in vendor  getStockDetails Service : " + e.getMessage());
            return StandardResposneUtil.internalServerErrorResponse();
        }
    }
}
