package com.laptop.code.laptop.Controller;

import com.laptop.code.laptop.entity.CustomerDetailsEntity;
import com.laptop.code.laptop.pojo.*;
import com.laptop.code.laptop.service.LTService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3008")
public class LTController {

    @Autowired
    LTService ltService;
//    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public ResponseEntity<StandardResponseMessage> login(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateNewUserPojo loginDetails) {
//        StandardResponseMessage result =ltService.doLogin(request, response, loginDetails.getUserId().toUpperCase(), loginDetails.getPwd().toUpperCase());
//        return ltService.getReturnResponse(result);
//    }

//    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject addUser(@RequestBody CreateNewUserPojo createNewUserPojo) {
//            return ltService.addUser(createNewUserPojo);
//    }

//    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public List<NewUserEntity> getUserList() {
//        List<NewUserEntity> response = ltService.getAllUser();
//
//        return response;
//    }

//    @RequestMapping(value = "/remover/user", method = RequestMethod.DELETE, produces = {"application/json; charset=utf-8"})
//    public JSONObject removeUser(@RequestBody CreateNewUserPojo userId) {
//       return ltService.removeUser(userId.getUserId());
//    }

//    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject forgotPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody OtpPojo forgetId) {
//        return ltService.forgetPassword(request, response, forgetId.getUserId());
//    }

//    @RequestMapping(value = "/verifyOtp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject getVerifyOtp(@RequestBody OtpPojo otpPojo) {
//        System.out.println("Enters verify Otp Controller");
//        return ltService.getVerifyOtp(otpPojo.getOtp());
//    }

//    @RequestMapping(value = "/newPassword", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject newPassword(HttpServletRequest request, @RequestBody PasswordPojo passwordPojo) {
//        System.out.println(passwordPojo.getNewPassword() + " edd" + passwordPojo.getConfirmPassword());
//        return ltService.newPassword(request, passwordPojo.getNewPassword(), passwordPojo.getConfirmPassword());
//    }
//    @RequestMapping(value = "/addIntake", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject getIntake(@RequestBody CustomerDetailsPojo customerDetailsPojo) {
//        JSONObject json = new JSONObject();
//        return ltService.addIntake(customerDetailsPojo);
//    }

//    @RequestMapping(value = "/getByStatus", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject getByStatus(@RequestBody StatusPojo statusPojo) {
//        return ltService.getByStatus(statusPojo.getStatus());
//
//    }
//    @RequestMapping(value = "/updateRejectedStatus", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject updateRejectedStatus(@RequestBody StatusPojo statusPojo) {
//        JSONObject json = new JSONObject();
//        ltService.updateRejectedStatus(statusPojo.getIntakeNo(), statusPojo.getStatus(), statusPojo.getRejectedReason());
//        json.put("message", "Successfully Updated");
//        return json;
//    }
//    @RequestMapping(value = "/updateStatus", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject updateStatus(@RequestBody StatusPojo statusPojo) {
//        JSONObject json = new JSONObject();
//        String list = ltService.updateStatus(statusPojo.getIntakeNo(), statusPojo.getStatus());
//        json.put("message", list);
//        return json;
//    }

//    @PostMapping("/search/user")
//    @ResponseBody
//    public JSONObject searchUser(@RequestBody StatusPojo ltPojo) {
//        return ltService.searchUser(ltPojo.getMobileNo());
//    }
//    @RequestMapping(value = "/problemIdentified", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject problemIdentified(@RequestBody CustomerDetailsPojo ltPojo) {
//        return ltService.problemIdentified(ltPojo.getIntakeNo(), ltPojo.getProblemIdentified(), ltPojo.getPrice());
//    }

    @RequestMapping(value = "/getPriceList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public List<CustomerDetailsEntity> getPriceList(@RequestBody PriceListPojo priceListPojo) {
        return ltService.getPriceList(priceListPojo.getDate(), priceListPojo.getBranch());
    }

    @RequestMapping(value = "/getAllPriceList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public JSONObject getAllPriceList() {
        System.out.println(BranchPojo.branch);
        return ltService.getAllPriceList(BranchPojo.branch);
    }

//    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject changePassword(HttpServletRequest request, @RequestBody PasswordPojo passwordPojo) {
//        return ltService.changePassword(request, passwordPojo.getOldPassword(), passwordPojo.getNewPassword(), passwordPojo.getConfirmPassword());
//    }

//    @RequestMapping(value = "/getSwitchBranch", method = RequestMethod.GET, produces = {"application/json;"})
//    public JSONObject getSwitchBranch(HttpServletRequest request) {
//        JSONObject res = new JSONObject();
//        System.out.println("enters get switch branch ");
//        return ltService.getSwitchBranchName(request);
//    }

//    @RequestMapping(value = "/switchBranch", method = RequestMethod.POST, produces = {"application/json;"})
//    public JSONObject getSwitchBranch(HttpServletRequest request, @RequestBody StatusPojo branch) {
//        JSONObject response = new JSONObject();
//        if ("YES".equals(BranchPojo.adminAccess)) {
//            BranchPojo.branch = branch.getBranch();
//            System.out.println(BranchPojo.branch + "Switch branch");
//            ltService.updateSwitchBranchName(request, branch.getBranch());
//            response.put("message", "Successfully Branch Switched");
//        } else
//            response.put("message ", " Access Denied... Failed to Switch Branch");
//
//        return response;
//    }

    //    @RequestMapping(value ="/getBranchName", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public JSONObject getOwnBranchName(HttpServletRequest request){
//    return ltService.getownBranchName(request);
//    }
//    @RequestMapping(value = "/getBranchList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public JSONObject getBranchName(HttpServletRequest request) {
//        return ltService.getBranchName(request);
//    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public JSONObject logout(HttpServletRequest request,HttpServletResponse response) {
//        return ltService.logout(request,response);
//    }

//    @RequestMapping(value = "/userDetails", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public JSONObject userDetails(HttpServletRequest request) {
//        return ltService.userDetails(request);
//    }

    //serach by status
//    @RequestMapping(value = "/searchIntakeByStatus", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
//    public JSONObject getSearchList(HttpServletRequest request,@RequestBody Statu,sPojo statusPojo) {
//        return ltService.getStatusList(statusPojo.getIntakeNo());
//    }
//    @RequestMapping(value = "/getusersList", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
//    public JSONObject getUsersList() {
//        return ltService.getUsersList();
//    }

//    @RequestMapping(value="/addVenStockDetails",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject addVenStockDetails(@RequestBody  StockListPojo stockListPojo){return ltService.addVenStockDetails(stockListPojo);}

//    @RequestMapping(value="/addBranchStockDetails",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject addBranchStockDetails(@RequestBody  StockListPojo stockListPojo){return ltService.addBranchStockDetails(stockListPojo);}

//    @RequestMapping(value="/transferStock",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject transferStock(@RequestBody  StockListPojo stockListPojo){return ltService.transferStock(stockListPojo.getStockId(),stockListPojo.getBranchName());}

//    @RequestMapping(value="/UpdateHandOutStatus",method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
//    public JSONObject UpdateHandOutStatus(@RequestBody  StatusPojo statusPojo){return ltService.UpdateHandOutStatus(statusPojo.getIntakeNo());}
//
////    @RequestMapping(value="/fetchUser/details",method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
////    public JSONObject fetchUserDetails(@RequestBody StatusPojo statusPojo){
////        return ltService.fetchUserDetails(statusPojo.getSearchNo());
////    }
}

