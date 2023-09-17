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

import java.util.Collections;

@Service
public class UpdateStatusService {
    @Autowired
    CustomerDetailsRepository customerDetailsRepository;
    @Autowired
    IntakePdfService service;
    private static Logger logger = LoggerFactory.getLogger(UpdateStatusService.class);

    public StandardResponseMessage problemIdentified(String intakeNo, String problemIdentified, long price){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try {
            if (Validators.hasData(intakeNo) && Validators.hasData(problemIdentified) && Validators.hasData(price)) {
                customerDetailsRepository.problemIdentified(intakeNo, problemIdentified, price);
                standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList("Successfully Updated"));
            }
            else
                standardResponseMessage= StandardResposneUtil.notFound();
        }
        catch (Exception e){
            logger.error("Error Occurred in problemIdentified Service : "+e.getMessage());
            standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
    public StandardResponseMessage UpdateHandOutStatus(String intakeNo,String status){
        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
        try{
            if(status.equals("Ready to Delivery")){
                customerDetailsRepository.updateHandoutStatus(intakeNo,"Delivered");
            }
            else if(status.equals("Ready to Return")){
                customerDetailsRepository.updateHandoutStatus(intakeNo,"Returned");
            }
            standardResponseMessage= StandardResposneUtil.successResponse(Collections.singletonList("Delivered"));
        }
        catch (Exception e){
           logger.error("Error in handout "+e);
           standardResponseMessage= StandardResposneUtil.badRequestResponse();
        }
        return standardResponseMessage;
    }
}
