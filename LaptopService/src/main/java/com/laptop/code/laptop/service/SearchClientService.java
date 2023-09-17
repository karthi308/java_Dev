package com.laptop.code.laptop.service;

import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import com.laptop.code.laptop.repository.CustomerDetailsRepository;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchClientService {
    @Autowired
    CustomerDetailsRepository customerDetailsRepo;
    private static Logger logger = LoggerFactory.getLogger(SearchClientService.class);

    public StandardResponseMessage fetchAllClientDetails(String searchNo){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            if (searchNo.length() == 10) {
                standardResponseMessage = fetchAllClientDetailsResposne(customerDetailsRepo.fetchAllClientDetails(Long.parseLong(searchNo)));
            } else {
                standardResponseMessage = fetchAllClientDetailsResposne(customerDetailsRepo.fetchAllClientDetails(searchNo));
            }
        }
        catch (Exception e){
            logger.info("Error in Fetch User Details "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage fetchAllClientDetailsResposne(List<CustomerDetailsEntity> clientDetailsList) {
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        if(Validators.hasData(clientDetailsList)){
            standardResponseMessage= StandardResposneUtil.successResponse(clientDetailsList);
        }
        else
            standardResponseMessage= StandardResposneUtil.notFound();
        return standardResponseMessage;
    }
    public StandardResponseMessage fetchHandInClientDetails(String searchNo){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
        if (searchNo.length() == 10) {
            standardResponseMessage = fetchAllClientDetailsResposne(customerDetailsRepo.fetchHandInClientDetails(Long.parseLong(searchNo)));
        } else
            standardResponseMessage = fetchAllClientDetailsResposne(customerDetailsRepo.fetchHandInClientDetails(searchNo));
        }
        catch (Exception e){
        System.out.println("Error in Fetch User Details "+e);
        logger.info("Error Occurred in fetchHandInClientDetails Service : "+e.getMessage());
        }
        return standardResponseMessage;
    }

    public StandardResponseMessage getCustomerDetails(long mobileNo){
        List<CustomerDetailsEntity> customerDetails = customerDetailsRepo.findByMobileNumber(mobileNo);
        if(Validators.hasData(customerDetails))
            return StandardResposneUtil.successResponse(customerDetails);
        else
            return StandardResposneUtil.notFound();
    }
}
