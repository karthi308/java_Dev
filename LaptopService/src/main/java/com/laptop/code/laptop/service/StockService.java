package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.BranchStockDetailsEntity;
import com.laptop.code.laptop.entity.VendorDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.pojo.StockDetailsPojo;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.repository.VendorDetailsRepository;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StockService {
    @Autowired
    VendorDetailsRepository vendorDetailsRepo;
    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;
    private static Logger logger = LoggerFactory.getLogger(StockService.class);

    public StandardResponseMessage addVenStockDetails(StockDetailsPojo stockListPojo){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try{
            VendorDetailsEntity vendorDetailsEntity=new VendorDetailsEntity();
            vendorDetailsEntity.setStock_id(1001);
            vendorDetailsEntity.setStockName(stockListPojo.getStockName());
            vendorDetailsEntity.setVendorName(stockListPojo.getVendorName());
            vendorDetailsEntity.setPrice(stockListPojo.getPrice());
            vendorDetailsEntity.setWarranty(stockListPojo.getWarranty());
            vendorDetailsEntity.setLocation(stockListPojo.getLocation());
            vendorDetailsRepo.save(vendorDetailsEntity);
            List<VendorDetailsEntity> vendorDetailsEntityList=new ArrayList<>();
            vendorDetailsEntityList.add(vendorDetailsEntity);
            standardResponseMessage= StandardResposneUtil.successResponse(vendorDetailsEntityList);
        }
        catch (Exception e){
            logger.error("Error in addVenStockDetails "+e);
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }

}
