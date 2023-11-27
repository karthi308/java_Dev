package com.laptop.code.laptop.service;

import com.laptop.code.laptop.Controller.LTController;
import com.laptop.code.laptop.pojo.*;
import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import com.laptop.code.laptop.repository.BranchStockDetailsRepository;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.repository.CustomerDetailsRepository;
import com.laptop.code.laptop.repository.VendorDetailsRepository;
import com.laptop.code.laptop.util.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LTService {

    @Autowired
    UserDetailsRepository newUserRepository;

    @Autowired
    OtpPojo otpPojo;
    @Autowired
    CustomerDetailsRepository userDetailsRepository;

    @Autowired(required = false)
    StatusPojo statusPojo;

    @Autowired(required = false)
    UserDetailsEntity newUserEntity;

    @Autowired
    SendMail sendMail;

    @Autowired
    VendorDetailsRepository vendorDetailsRepo;

    @Autowired
    BranchStockDetailsRepository branchStockDetailsRepository;
    private static Logger logger = LoggerFactory.getLogger(LTController.class);

    //DO Login
//    public StandardResponseMessage doLogin(HttpServletRequest request, HttpServletResponse response, String userId, String pwd){
////        redisTemplate.opsForValue().set("userid ","EK3008");
////        System.out.println(redisTemplate.opsForValue().get("userid"));
//        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
//        try {
//            standardResponseMessage=hasValidUser(response,userId,pwd);
//        }
//        catch (Exception e){
//            standardResponseMessage=DefaultResponseMessage.badRequestResponse();
//            logger.error("Error in Login Service "+e);
//        }
//        return standardResponseMessage;
//    }
//    public StandardResponseMessage hasValidUser(HttpServletResponse response, String userId, String pwd){
//        StandardResponseMessage standardResponseMessage=new StandardResponseMessage();
//        Optional<NewUserEntity> userDetails=newUserRepository.findById(userId);
//        String dbUserId="";
//        String dbPwd="";
//        List<DoLoginResponsePojo> doLoginResponseList=new ArrayList<>();
//        DoLoginResponsePojo doLoginResponsePojo=new DoLoginResponsePojo();
//        if (Validators.hasData(userDetails)) {
//            BranchPojo.branch = userDetails.get().getBranch();
//            dbUserId = userDetails.get().getUserId().toString();
//            dbPwd = userDetails.get().getPwd().toString();
//            if (userId.equals(dbUserId) && pwd.equals(dbPwd)) {
//                doLoginResponsePojo.setUserKey(setUserDetails(response,userDetails));
//                doLoginResponseList.add(doLoginResponsePojo);
//                standardResponseMessage=DefaultResponseMessage.successResponse(doLoginResponseList);
//            }
//            else if(userId.equals(dbUserId) && !pwd.equals(dbPwd)){
//                doLoginResponsePojo.setLoginStatus(Constant.INVALID_PASSWORD);
//                doLoginResponseList.add(doLoginResponsePojo);
//                standardResponseMessage=DefaultResponseMessage.unAuthorizedResponse();
//                standardResponseMessage.setData(doLoginResponseList);
//            }
//        }
//        else{
//            doLoginResponsePojo.setLoginStatus(Constant.INVALID_USER);
//            doLoginResponseList.add(doLoginResponsePojo);
//            standardResponseMessage=DefaultResponseMessage.unAuthorizedResponse();
//            standardResponseMessage.setData(doLoginResponseList);
//        }
//        return standardResponseMessage;
//    }
//    public String setUserDetails(HttpServletResponse response, Optional<NewUserEntity> userDetails){
//        String userKey = UUID.randomUUID().toString();
//        userKey = userKey.replace("-", "");
//        setCookiesUserIdAndUserKey(response, userDetails.get().getUserId(),userKey);
//        NewUserEntity newUserEntity = new NewUserEntity();
//        newUserEntity.setUserId(userDetails.get().getUserId());
//        newUserEntity.setUserName(userDetails.get().getUserName());
//        newUserEntity.setPwd(userDetails.get().getPwd());
//        newUserEntity.setAdminAccess(userDetails.get().getAdminAccess());
//        newUserEntity.setMailId(userDetails.get().getMailId());
//        newUserEntity.setBranch(userDetails.get().getBranch());
//        newUserEntity.setSwitchBranchName(userDetails.get().getSwitchBranchName());
//        newUserEntity.setCreatedTime(userDetails.get().getCreatedTime());
//        newUserEntity.setUserKey(userKey);
//        newUserEntity.setUpdateTime(new Date());
//        newUserRepository.save(newUserEntity);
//        return userKey;
//    }
//    public void setCookiesUserIdAndUserKey(HttpServletResponse response,String userId,String appKey){
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","GET,PUT,POST,DELETE,PATCH,OPTIONS");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
//
//        Cookie userIdCookieRemove = new Cookie("userId", userId);
//        response.addCookie(userIdCookieRemove);
//        Cookie usertokenCookieRemove = new Cookie("userKey", appKey);
//        response.addCookie(usertokenCookieRemove);
//    }
    //Do Login

    //////////////////////////////////////////////////////////////

//    public JSONObject addUser(CreateNewUserPojo createNewUserPojo){
//            NewUserEntity newUserEntity = new NewUserEntity();
//            newUserEntity.setUserId(createNewUserPojo.getUserId().toUpperCase());
//            newUserEntity.setUserName(createNewUserPojo.getUserName().toUpperCase());
//            newUserEntity.setPwd(createNewUserPojo.getBranch().toUpperCase()+"@123");
//            newUserEntity.setAdminAccess(createNewUserPojo.getAdminAccess());
//            newUserEntity.setBranch(createNewUserPojo.getBranch().toUpperCase());
//            newUserEntity.setMailId(createNewUserPojo.getMailId().toLowerCase());
//            newUserEntity.setSwitchBranchName(createNewUserPojo.getBranch().toUpperCase());
//            newUserEntity.setCreatedTime(new Date());
//            newUserRepository.save(newUserEntity);
//            JSONObject response=new JSONObject();
//            response.put("message","Successfully Created");
//            return response;
//    }
//    public JSONObject removeUser(String userId){
//        JSONObject response=new JSONObject();
//            newUserRepository.deleteById(userId.toUpperCase());
//            response.put("message ",true);
//        return response;
//    }
//    public JSONObject changePassword(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
//        HttpSession session=request.getSession();
//        String userId=CommonFunction.getUserId(request);
//        Optional<NewUserEntity> list=newUserRepository.findById(userId);
//       JSONObject json=new JSONObject();
//       if(list!=null && oldPassword.equals(list.get().getPwd())){
//           if(newPassword.equals(confirmPassword)){
//                NewUserEntity newUserEntity1=new NewUserEntity();
//               newUserEntity1.setUserId(list.get().getUserId());
//               newUserEntity1.setUserName(list.get().getUserName());
//               newUserEntity1.setPwd(confirmPassword);
//               newUserEntity1.setAdminAccess(list.get().getAdminAccess());
//               newUserEntity1.setUserKey(list.get().getUserKey());
//               newUserEntity1.setBranch(list.get().getBranch());
//               newUserEntity1.setSwitchBranchName(list.get().getSwitchBranchName());
//               newUserEntity1.setCreatedTime(list.get().getCreatedTime());
//               newUserEntity1.setUpdateTime(new Date());
//               newUserEntity1.setMailId(list.get().getMailId());
//               newUserRepository.save(newUserEntity1);
//               json.put("message","Password successfully changed");
//               session.setAttribute("pwd",confirmPassword);
//           }
//           else
//           json.put("message","New Password and Confirm Password Not Matching..");
//       }
//       else
//           json.put("message","Incorrect Old Password");
//
//       return json;
//    }






//    public JSONObject logout(HttpServletRequest request,HttpServletResponse response){
//        String userId=getCookiesUserId(request);
//        JSONObject res=new JSONObject();
//        NewUserEntity newUserEntity1=new NewUserEntity();
//       Optional<NewUserEntity> list= newUserRepository.findById(userId);
//        newUserEntity1.setUserId(list.get().getUserId());
//        newUserEntity1.setUserName(list.get().getUserName());
//        newUserEntity1.setPwd(list.get().getPwd());
//        newUserEntity1.setAdminAccess(list.get().getAdminAccess());
//        newUserEntity1.setBranch(list.get().getBranch());
//        newUserEntity1.setSwitchBranchName(list.get().getSwitchBranchName());
//        newUserEntity1.setUserKey(null);
//        newUserEntity1.setMailId(list.get().getMailId());
//        newUserEntity1.setCreatedTime(list.get().getCreatedTime());
//        newUserEntity1.setUpdateTime(new Date());
//        newUserRepository.save(newUserEntity1);
//        Cookie removeCookiesuserId=new Cookie("userId","");
//        Cookie removeCookiesuserKey=new Cookie("userKey","");
//        response.addCookie(removeCookiesuserId);
//        response.addCookie(removeCookiesuserKey);
//        res.put("message","Successfully Logged Out");
//
//        return res;
//    }
//    public List<NewUserEntity> getAllUser(){
//        List<NewUserEntity> list =newUserRepository.findAll();
//        System.out.println("size "+list.size());
//        for(int i=0;i<list.size();i++){
//            System.out.println("for loop");
//            list.get(i).setPwd(null);
//        }
//        return list;
//    }
//
//    public Optional<NewUserEntity> getUser(String userId){
//        Optional<NewUserEntity> user=null;
//        try {
//            user = newUserRepository.findById(userId);
//        }
//        catch (Exception e){
//
//        }
//        return user;
//    }

//    public JSONObject addIntake(CustomerDetailsPojo customerDetails){
//        JSONObject json=new JSONObject();
//        UserDetailsEntity userDetailsEntity=new UserDetailsEntity();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
//        String date = simpleDateFormat.format(new Date());
//        date=date.replace("-","");
//        String intake="";
//        StringBuffer sbfDate = new StringBuffer(date);
//        sbfDate.delete(6, 8);
//        System.out.println("buf "+sbfDate);
//        long dt=0;
//        dt = userDetailsRepository.countDistinctYDByDateBybranch(sbfDate + "%",BranchPojo.branch);
//        System.out.println(dt+customerDetails.getSlNo());
//        if(dt<=9){
//            intake="000";
//        }
//        else if(dt>=10 && dt<=99){
//            intake="00";
//        }
//        else
//        {
//            intake="0";
//        }
//        userDetailsEntity.setYD(date);
//        userDetailsEntity.setIntakeNo(BranchPojo.branch+sbfDate+intake+(dt+1));
//        userDetailsEntity.setName(customerDetails.getName());
//        userDetailsEntity.setMobileNumber(Long.parseLong(customerDetails.getMobileNumber()));
//        userDetailsEntity.setMobileNumber(Long.parseLong(customerDetails.getAlternativeMobileNumber()));
//        userDetailsEntity.setAddress(customerDetails.getAddress());
//        userDetailsEntity.setCity(customerDetails.getCity());
//        userDetailsEntity.setState(customerDetails.getState());
//        userDetailsEntity.setPincode(customerDetails.getPincode());
//        userDetailsEntity.setMailId(customerDetails.getMailId());
//        userDetailsEntity.setMake(customerDetails.getMake());
//        userDetailsEntity.setModel(customerDetails.getModel());
//        userDetailsEntity.setSlNo(customerDetails.getSlNo());
//        userDetailsEntity.setBtSlNo(customerDetails.getBatterySlNo());
//        userDetailsEntity.setWithAdapter(customerDetails.isWithAdapter());
//        userDetailsEntity.setAdapterSlNo(customerDetails.getAdapterSlNo());
//        userDetailsEntity.setProblem(customerDetails.getProblem());
//        userDetailsEntity.setStatus("New");
//        userDetailsEntity.setDamages(customerDetails.getDamages());
//        userDetailsEntity.setPrice(0);
//        userDetailsEntity.setBranch(BranchPojo.branch);
//        userDetailsEntity.setOthers(customerDetails.getOthers());
//        customerDetails.setIntakeNo(BranchPojo.branch+sbfDate+intake+(dt+1));
//        json.put("data",customerDetails);
//        CustomerDetailsPojo customerDetails1=new CustomerDetailsPojo();
//        userDetailsRepository.save(userDetailsEntity);
//
//        return json;
//    }

//    public JSONObject getByStatus(String status){
//        List<UserDetailsEntity>list=null;
//        JSONObject json = new JSONObject();
//            list = userDetailsRepository.getNewStatusByBranch(BranchPojo.branch,status);
//        System.out.println("prblm "+list);
//        if(list.size()>0){
//            json.put("data", list);
//            json.put("message",true);
//        }
//        else{
//            json.put("message",false);
//        }
//        return json;
//        }

//        public String updateStatus(String intakeNo,String status){
//            userDetailsRepository.updateStatus(intakeNo,status);
//            return "Successfully Status Updated";
//        }
//    public String updateRejectedStatus(String intakeNo,String status, String rejectedReason){
//        userDetailsRepository.updateRejectedStatus(intakeNo, status, rejectedReason);
//        return "Successfully Status Updated";
//    }
//    public JSONObject searchAllCustomer(String mobileNumber){
//            List<UserDetailsEntity> list=null;
//            JSONObject response=new JSONObject();
//            if(mobileNumber!=null && mobileNumber.length() == 10){
//                list = userDetailsRepository.getByMobileNo(Long.parseLong(mobileNumber));
//            }
//            else if(mobileNumber!=null){
//                list = userDetailsRepository.getByIntakeNo(mobileNumber);
//            }
//            if(list!=null && list.size()>0){
//                response.put("message",true);
//                response.put("data",list);
//            }
//            else{
//                response.put("message","No Record found");
//            }
//            return response;
//    }
//    public JSONObject fetchUserDetails(String searchNo){
//        JSONObject response=new JSONObject();
//        try {
//            List<UserDetailsEntity> list = null;
//            if (searchNo.length() == 10) {
//                list = userDetailsRepository.getByMobileNo(Long.parseLong(searchNo));
//            } else {
//                list = userDetailsRepository.getByIntakeNo(searchNo);
//            }
//            response.put("data", list);
//        }
//        catch (Exception e){
//            System.out.println("Error in Fetch User Details "+e);
//            response.put("message",false);
//        }
//       return response;
//    }


//        public JSONObject problemIdentified(String intakeNo,String problemIdentified,long price){
//            JSONObject response =new JSONObject();
//            if(!intakeNo.equals("") && !problemIdentified.equals("") && price >0) {
////                userDetailsRepository.problemIdentified(intakeNo, problemIdentified, price, "Waiting for Confirmation");
//                response.put("message","Successfully Updated");
//            }
//            return response;
//        }

//        public JSONObject UpdateHandOutStatus(String intakeNo){
//        JSONObject response=new JSONObject();
//            try{
////                userDetailsRepository.updateHandoutStatus(intakeNo,"Delivered");
//                response.put("message ",true);
//            }
//            catch (Exception e){
//                System.out.println("Error in handout "+e);
//                response.put("message ",false);
//            }
//            return response;
//        }
        public List<CustomerDetailsEntity> getPriceList(String date, String branch){
            StringBuffer sbf = new StringBuffer(date);
            date.replace("/","");
            if(date.length()!=8){
                date+="%";
            }
            List<CustomerDetailsEntity> list=null;
//            if(BranchPojo.adminAccess.equals("YES")) {
//                list = userDetailsRepository.getPriceList(date);
//            }
//            else{
//                list = userDetailsRepository.getPriceListByBranch(date,branch);
//            }
            return list;
        }

        public JSONObject getAllPriceList(String branch){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date());
            date=date.replace("-","");
            StringBuffer sbfDate = new StringBuffer(date);
            String today=date;
            String month=sbfDate.delete(6, 8).toString()+"%";
            String year=sbfDate.delete(4,8).toString()+"%";
            JSONObject data=new JSONObject();
            JSONObject priceList=new JSONObject();
            List<CustomerDetailsEntity> list=null;
            List<CustomerDetailsEntity> list1=null;
            List<CustomerDetailsEntity> list2=null;
//            if("LSINJ".equals(BranchPojo.branch)){
//                list=userDetailsRepository.getPriceList(today);
//                list1=userDetailsRepository.getPriceList(month);
//                list2=userDetailsRepository.getPriceList(year);
//            }
//            else{
//                list=userDetailsRepository.getPriceListByBranch(today,branch);
//                list1=userDetailsRepository.getPriceListByBranch(month,branch);
//                list2=userDetailsRepository.getPriceListByBranch(year,branch);
//            }

            int todayTotal=getPrice(list);
            int monthTotal=getPrice(list1);
            int yearTotal=getPrice(list2);
            priceList.put("today",todayTotal);
            priceList.put("month",monthTotal);
            priceList.put("year",yearTotal);
            data.put("data",priceList);

            return data;
        }
        public int getPrice(List<CustomerDetailsEntity> userDetailsEntityList){
        int length=userDetailsEntityList.size();
        int total=0;
        JSONObject json=new JSONObject();


        for(int i=0;i<length;i++){
            total+=userDetailsEntityList.get(i).getPrice();
            if(!userDetailsEntityList.get(i).getBranch().equals(json.get(userDetailsEntityList.get(i).getBranch()))){
                json.put(userDetailsEntityList.get(i).getBranch(),total);
            }
        }
        return total;
        }
//        public JSONObject forgetPassword(HttpServletRequest request,HttpServletResponse res,String userId) {
//            StaticuserId.userId=userId.toUpperCase();
//            Optional<NewUserEntity> list = newUserRepository.findById(userId.toUpperCase());
//            String mailId=list.get().getMailId();
//            boolean mailStat = false;
//            JSONObject response=new JSONObject();
//            try {
//                if (!list.isEmpty() && list.get().getMailId()!=null) {
//                        mailStat = true;
//                }
//                else{
//                    response.put("message","Invalid User Id");
//                }
//                if (mailStat) {
//                    int otp=getRandomOtp();
//                    String mailResponse=sendMail.mail(mailId,userId,"One Time Password","Use "+otp+" to reset your password.Don't share this otp to anyone.");
//                    otpPojo.setOtp(otp);
//                    otpPojo.setUserId(userId);
//                    otpPojo.setMailId(mailId);
//                    response.put("message",mailResponse+"");
//                }
//            }
//            catch (Exception e){
//                System.out.println("error in mail otp "+e);
//            }
//            return response;
//        }
//        public int getRandomOtp(){
//            SecureRandom rand = new SecureRandom();
//            int min = 1000;
//            int max = 9999;
//            int otp = (int)Math.floor(Math.random() * (max - min + 1) + min);
//            return otp;
//        }
//        public JSONObject resendOtp(){
//            JSONObject response=new JSONObject();
//            String userId=otpPojo.getUserId();
//            String mailId=otpPojo.getMailId();
////            int otp=getRandomOtp();
//            String mailResponse=sendMail.mail(mailId,userId,"One Time Password","Use "+otp+" to reset your password.Don't share this otp to anyone.");
//            response.put("message",mailResponse);
//            return response;
//        }
//        public JSONObject getVerifyOtp(int mailOtp){
//        int otp=otpPojo.getOtp();
//        System.out.println(otp);
//        JSONObject response=new JSONObject();
//        response.put("message","Invalid otp");
//        if(otp==mailOtp){
//            response.put("message","Success");
//        }
//        return response;
//        }

//        public JSONObject newPassword(HttpServletRequest request, String newPassword, String confirmPassword){
//            String userId= StaticuserId.userId;
//            System.out.println("keyssss "+userId);
//            NewUserEntity newUser=new NewUserEntity();
//            JSONObject response=new JSONObject();
//            response.put("message","New Password and ConfirmPassword Not Matching");
//                if(newPassword.equals(confirmPassword)){
//                   Optional<NewUserEntity> list= newUserRepository.findById(userId);
//                    newUser.setUserId(list.get().getUserId());
//                    newUser.setUserName(list.get().getUserName());
//                    newUser.setPwd(confirmPassword);
//                    newUser.setBranch(list.get().getBranch());
//                    newUser.setUserKey(list.get().getUserKey());
//                    newUser.setCreatedTime(list.get().getCreatedTime());
//                    newUser.setMailId(list.get().getMailId());
//                    newUser.setUpdateTime(new Date());
//                    newUser.setAdminAccess(list.get().getAdminAccess());
//                    newUser.setSwitchBranchName(list.get().getSwitchBranchName());
//                    newUserRepository.save(newUser);
//                    System.out.println("succesfully changed");
//                    response.put("message","Successfully Changed");
//                    sendMail.mail(newUser.getMailId(),newUser.getUserId(),"Password Changed Successfully","Your Password Changed Successfully..");
//                }
//                return response;
//        }

        public JSONObject getownBranchName(HttpServletRequest request){
        String userId=request.getHeader(Constant.USER_ID);
        System.out.println("userID "+userId);
        JSONObject response =new JSONObject();
            Optional<UserDetailsEntity> list= newUserRepository.findById(userId);
            response.put("ownBranch",list.get().getBranchName());
            return response;
        }
//        public JSONObject getBranchName(HttpServletRequest request){
//           List<NewUserEntity> list= newUserRepository.findAll();
//            List<String> lst=new ArrayList<>();
//            JSONObject response=new JSONObject();
//           for(int i=0;i<list.size();i++){
//              lst.add(list.get(i).getBranch());
//           }
//           System.out.println(lst+" response ");
//           response.put("data",lst);
//            return response;
//        }
//        public String getCookiesUserId(HttpServletRequest request){
//            Cookie[] cookies = request.getCookies();
//            String userId="";
//            if (cookies != null) {
//                for (int i = 0; i < cookies.length; i++) {
//                    if (cookies[i].getName().equals("userId")) {
//                        userId = cookies[i].getValue();
//                    }
//                }
//            }
//            return userId;
//        }
//
//    public String getCookiesUserKey(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        String userKey="";
//        if (cookies != null) {
//            for (int i = 0; i < cookies.length; i++) {
//                if (cookies[i].getName().equals("userId")) {
//                    userKey = cookies[i].getValue();
//                }
//            }
//        }
//        return userKey;
//    }


    public Map<String,String> testnew(HttpServletRequest request, HttpServletResponse response,String userId,String appKey){
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","GET,PUT,POST,DELETE,PATCH,OPTIONS");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");


        Cookie userIdCookieRemove = new Cookie("userId", userId);
        response.addCookie(userIdCookieRemove);
        Cookie usertokenCookieRemove = new Cookie("appKey", appKey);
        response.addCookie(usertokenCookieRemove);
        Map<String,String> dd = new HashMap<>();
        dd.put("hey","heyy");
        return dd;
    }
//    public JSONObject getSwitchBranchName(HttpServletRequest request){
//        String userId =CommonFunction.getUserId(request);
//        JSONObject res=new JSONObject();
//        Optional<NewUserEntity> userList=newUserRepository.findById(userId);
//        if(userList!=null){
//            res.put("message",true);
//            res.put("data",userList.get().getSwitchBranchName());
//        }
//        else {
//            res.put("message",false);
//        }
//        System.out.println("branch "+userList.get().getSwitchBranchName());
//        return res;
//    }
//    public JSONObject updateSwitchBranchName(HttpServletRequest request, String branchName ){
//        String userId =CommonFunction.getUserId(request);
//        JSONObject res=new JSONObject();
//        Optional<NewUserEntity> userList=newUserRepository.findById(userId);
//        NewUserEntity newUserEntity = new NewUserEntity();
//        newUserEntity.setUserId(userList.get().getUserId());
//        newUserEntity.setUserName(userList.get().getUserName());
//        newUserEntity.setPwd(userList.get().getPwd());
//        newUserEntity.setMailId(userList.get().getMailId());
//        newUserEntity.setAdminAccess(userList.get().getAdminAccess());
//        newUserEntity.setBranch(userList.get().getBranch());
//        newUserEntity.setUserKey(userList.get().getUserKey());
//        newUserEntity.setSwitchBranchName(branchName);
//        newUserEntity.setUpdateTime(new Date());
//        newUserRepository.save(newUserEntity);
//         res.put("message","branch updated");
//         return res;
//    }
//    public JSONObject userDetails(HttpServletRequest request){
//        String userId=CommonFunction.getUserId(request);
//        JSONObject response=new JSONObject();
//        JSONObject data=new JSONObject();
//        Optional<NewUserEntity> userList=newUserRepository.findById(userId);
//        if(userList!=null) {
//            response.put("message", true);
//            data.put("userId",userList.get().getUserId());
//            data.put("userName",userList.get().getUserName());
//            data.put("userBranch",userList.get().getBranch());
//            data.put("mailId",userList.get().getMailId());
//            data.put("adminAccess",userList.get().getAdminAccess());
//            response.put("data",data);
//        }
//        else{
//            response.put("message",false);
//        }
//        return response;
//    }
//    public JSONObject getStatusList(String number){
//        return searchUser(number);
//    }

    public JSONObject getUsersList(){
        JSONObject response=new JSONObject();
        List<UserDetailsEntity> userList=newUserRepository.findAll();
        if(userList!=null){
            response.put("message",true);
            response.put("data",userList);
        }
        else{
            response.put("message",false);
        }
        return response;
    }
//    public JSONObject addVenStockDetails(StockListPojo stockListPojo){
//        JSONObject response =new JSONObject();
//        try{
//            VendorDetailsEntity vendorDetailsEntity=new VendorDetailsEntity();
//            vendorDetailsEntity.setStock_id(1001);
//            vendorDetailsEntity.setStockName(stockListPojo.getStockName());
//            vendorDetailsEntity.setVendorName(stockListPojo.getVendorName());
//            vendorDetailsEntity.setPrice(stockListPojo.getPrice());
//            vendorDetailsEntity.setWrntyFrmDate(new Date().toString());
//            vendorDetailsEntity.setWrntyToDate(stockListPojo.getWarrntyToDate());
//            vendorDetailsEntity.setLocation(stockListPojo.getLocation());
//            vendorDetailsRepo.save(vendorDetailsEntity);
//            response.put("message",true);
//        }
//        catch (Exception e){
//            System.out.println("Error in addVenStockDetails "+e);
//            response.put("message",false);
//        }
//
//        return response;
//    }
//    public JSONObject addBranchStockDetails(StockListPojo stockListPojo){
//        JSONObject response =new JSONObject();
//        try{
//            BranchStockDetailsEntity branchStockDetailsEntity=new BranchStockDetailsEntity();
//            branchStockDetailsEntity.setStock_id(1001);
//            branchStockDetailsEntity.setStockName(stockListPojo.getStockName());
//            branchStockDetailsEntity.setBranchName(stockListPojo.getBranchName().toUpperCase());
//            branchStockDetailsEntity.setPrice(stockListPojo.getPrice());
//            branchStockDetailsEntity.setWrntyFrmDate(new Date().toString());
//            branchStockDetailsEntity.setWrntyToDate(stockListPojo.getWarrntyToDate());
//            branchStockDetailsEntity.setStatus("InStock");
//            branchStockDetailsRepository.save(branchStockDetailsEntity);
//            response.put("message",true);
//        }
//        catch (Exception e){
//            System.out.println("Error in addVenStockDetails "+e);
//            response.put("message",false);
//        }
//
//        return response;
//    }
//    public JSONObject transferStock(int stockId,String branchName){
//        JSONObject response=new JSONObject();
//        try{
//           BranchStockDetailsEntity list=branchStockDetailsRepository.getById(stockId);
//           if(list!=null){
//               BranchStockDetailsEntity branchStockDetailsEntity=new BranchStockDetailsEntity();
//               branchStockDetailsEntity.setStock_id(list.getStock_id());
//               branchStockDetailsEntity.setStockName(list.getStockName());
//               branchStockDetailsEntity.setPrice(list.getPrice());
//               branchStockDetailsEntity.setBranchName(branchName);
//               branchStockDetailsEntity.setWrntyFrmDate(list.getWrntyFrmDate());
//               branchStockDetailsEntity.setWrntyToDate(list.getWrntyToDate());
//               branchStockDetailsEntity.setStatus(list.getStatus());
//               response.put("message ",true);
//            }
//        }
//        catch(Exception e){
//            System.out.println("Error in transefer stock "+e);
//            response.put("message ",false);
//        }
//        return response;
//    }
    public ResponseEntity<StandardResponseMessage> getResponseMessage(StandardResponseMessage result) {
        try {
            if (result.getSystemMessageType() == PrincipalConstant.MESSAGE_TYPE_FAILED)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error occurred in Get ResponseMessage :" + e.getMessage());
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    public ResponseEntity<StandardResponseMessage> getReturnResponse(StandardResponseMessage result){
        try {
            if (result.getSystemMessage() == PrincipalConstant.MESSAGE_TYPE_400_BAD_REQUEST_ERROR)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            else if (result.getSystemMessage() == PrincipalConstant.MESSAGE_TYPE_401_UNAUTHORIZED_ERROR) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            } else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            StandardResponseMessage error = StandardResposneUtil.internalServerErrorResponse();
            logger.error("Error occurred in ClientListDetails: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

