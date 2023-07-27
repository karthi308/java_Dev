package com.laptop.code.laptop.Controller;


import com.laptop.code.laptop.service.SendMail;
import com.laptop.code.laptop.pojo.CreateNewUserPojo;
import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.*;
import com.laptop.code.laptop.service.LTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3008")
public class LTController {

    @Autowired
    LTService ltService;
    @Autowired
    SendMail sendMail;
//    @Autowired(required = false)
//    CreateNewUserPojo createNewUserPojo;


    @PostMapping("/parenttestnews")
    @ResponseBody
    public void testnew(HttpServletRequest request, HttpServletResponse response, @RequestBody String name) {

        System.out.println("new cookies " + name);
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers",
//                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods",
//                "GET,PUT,POST,DELETE,PATCH,OPTIONS");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
//
//
//        String userIdCookie = "parentidone12=value; Path=/; Secure; HttpOnly; SameSite=None";
//        String userTokenCookie = "parentidtwo12=value; Path=/; Secure; HttpOnly; SameSite=None";
//        response.addHeader(HttpHeaders.SET_COOKIE,userIdCookie);
//        response.addHeader(HttpHeaders.SET_COOKIE,userTokenCookie);
//
//        Cookie userIdCookieRemove = new Cookie("sample", "1231241h3u");
//        response.addCookie(userIdCookieRemove);
//        Cookie usertokenCookieRemove = new Cookie("parentidtwo", "value");
//        response.addCookie(usertokenCookieRemove);
//        Map<String,String> dd = new HashMap<>();
//        dd.put("hey","heyy");
        // return ;
        ltService.testnew(request, response, "E783", "value");
    }


    //    @PostMapping("/login")
//    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject login(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateNewUserPojo loginDetails) {
        System.out.println("enters login controller");
        //ltService.testnew(request,response,"E783","value");
//         ltService.login(request,response,createNewUserPojo.getUserId().toUpperCase(),createNewUserPojo.getPwd());
//        System.out.println("userid "+loginDetails.getUserId()+"vhhv "+loginDetails.getPwd());
        return ltService.login(request, response, loginDetails.getUserId().toUpperCase(), loginDetails.getPwd().toUpperCase());


    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject addUser(@RequestBody CreateNewUserPojo createNewUserPojo) {
            return ltService.addUser(createNewUserPojo);
    }

    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public List<NewUserEntity> getUserList() {
        List<NewUserEntity> response = ltService.getAllUser();

        return response;
    }

    @RequestMapping(value = "/remover/user", method = RequestMethod.DELETE, produces = {"application/json; charset=utf-8"})
    public JSONObject removeUser(@RequestBody CreateNewUserPojo userId) {
       return ltService.removeUser(userId.getUserId());
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject forgotPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody OtpPojo forgetId) {
        return ltService.forgetPassword(request, response, forgetId.getUserId());
    }

    @RequestMapping(value = "/verifyOtp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject getVerifyOtp(@RequestBody OtpPojo otpPojo) {
        System.out.println("Enters verify Otp Controller");
        return ltService.getVerifyOtp(otpPojo.getOtp());
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject newPassword(HttpServletRequest request, @RequestBody PasswordPojo passwordPojo) {
        System.out.println(passwordPojo.getNewPassword() + " edd" + passwordPojo.getConfirmPassword());
        return ltService.newPassword(request, passwordPojo.getNewPassword(), passwordPojo.getConfirmPassword());
    }
    @RequestMapping(value = "/newIntake", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject getIntake(@RequestBody LTPojo ltPojo) {
        JSONObject json = new JSONObject();
        return ltService.addIntake(ltPojo);
    }

    @RequestMapping(value = "/getByStatus", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject getByStatus(@RequestBody StatusPojo statusPojo) {
        return ltService.getByStatus(statusPojo.getStatus());

    }
    @RequestMapping(value = "/updateRejectedStatus", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public JSONObject updateRejectedStatus(@RequestBody StatusPojo statusPojo) {
        JSONObject json = new JSONObject();
        ltService.updateRejectedStatus(statusPojo.getIntakeNo(), statusPojo.getStatus(), statusPojo.getRejectedReason());
        json.put("message", "Successfully Updated");
        return json;
    }
    @RequestMapping(value = "/updateStatus", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public JSONObject updateStatus(@RequestBody StatusPojo statusPojo) {
        JSONObject json = new JSONObject();
        String list = ltService.updateStatus(statusPojo.getIntakeNo(), statusPojo.getStatus());
        json.put("message", list);
        return json;
    }

    @PostMapping("/search/user")
    @ResponseBody
    public JSONObject searchUser(@RequestBody StatusPojo ltPojo) {
        return ltService.searchUser(ltPojo.getMobileNo());
    }
    @RequestMapping(value = "/problemIdentified", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject problemIdentified(@RequestBody LTPojo ltPojo) {
        return ltService.problemIdentified(ltPojo.getIntakeNo(), ltPojo.getProblemIdentified(), ltPojo.getPrice());
    }

    @RequestMapping(value = "/getPriceList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public List<UserDetailsEntity> getPriceList(@RequestBody PriceListPojo priceListPojo) {
        return ltService.getPriceList(priceListPojo.getDate(), priceListPojo.getBranch());
    }

    @RequestMapping(value = "/getAllPriceList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public JSONObject getAllPriceList() {
        System.out.println(BranchPojo.branch);
        return ltService.getAllPriceList(BranchPojo.branch);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public JSONObject changePassword(HttpServletRequest request, @RequestBody PasswordPojo passwordPojo) {
        return ltService.changePassword(request, passwordPojo.getOldPassword(), passwordPojo.getNewPassword(), passwordPojo.getConfirmPassword());
    }

    @RequestMapping(value = "/getSwitchBranch", method = RequestMethod.GET, produces = {"application/json;"})
    public JSONObject getSwitchBranch(HttpServletRequest request) {
        JSONObject res = new JSONObject();
        System.out.println("enters get switch branch ");
        return ltService.getSwitchBranchName(request);
    }

    @RequestMapping(value = "/switchBranch", method = RequestMethod.POST, produces = {"application/json;"})
    public JSONObject getSwitchBranch(HttpServletRequest request, @RequestBody StatusPojo branch) {
        JSONObject response = new JSONObject();
        if ("YES".equals(BranchPojo.adminAccess)) {
            BranchPojo.branch = branch.getBranch();
            System.out.println(BranchPojo.branch + "Switch branch");
            ltService.updateSwitchBranchName(request, branch.getBranch());
            response.put("message", "Successfully Branch Switched");
        } else
            response.put("message ", " Access Denied... Failed to Switch Branch");

        return response;
    }

    //    @RequestMapping(value ="/getBranchName", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public JSONObject getOwnBranchName(HttpServletRequest request){
//    return ltService.getownBranchName(request);
//    }
    @RequestMapping(value = "/getBranchList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public JSONObject getBranchName(HttpServletRequest request) {
        return ltService.getBranchName(request);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public JSONObject logout(HttpServletRequest request,HttpServletResponse response) {
        return ltService.logout(request,response);
    }

    @RequestMapping(value = "/userDetails", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public JSONObject userDetails(HttpServletRequest request) {
        return ltService.userDetails(request);
    }

    //serach by status
    @RequestMapping(value = "/searchIntakeByStatus", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject getSearchList(HttpServletRequest request,@RequestBody StatusPojo statusPojo) {
        return ltService.getStatusList(statusPojo.getIntakeNo());
    }
    @RequestMapping(value = "/getusersList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public JSONObject getUsersList() {
        return ltService.getUsersList();
    }

    @RequestMapping(value="/addVenStockDetails",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public JSONObject addVenStockDetails(@RequestBody  StockListPojo stockListPojo){return ltService.addVenStockDetails(stockListPojo);}

//    @RequestMapping(value="/addBranchStockDetails",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject addBranchStockDetails(@RequestBody  StockListPojo stockListPojo){return ltService.addBranchStockDetails(stockListPojo);}

    @RequestMapping(value="/transferStock",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public JSONObject transferStock(@RequestBody  StockListPojo stockListPojo){return ltService.transferStock(stockListPojo.getStockId(),stockListPojo.getBranchName());}

    @RequestMapping(value="/UpdateHandOutStatus",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    public JSONObject UpdateHandOutStatus(@RequestBody  StatusPojo statusPojo){return ltService.UpdateHandOutStatus(statusPojo.getIntakeNo());}

    @RequestMapping(value="/fetchUser/details",method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public JSONObject fetchUserDetails(@RequestBody StatusPojo statusPojo){
        return ltService.fetchUserDetails(statusPojo.getSearchNo());
    }
}

