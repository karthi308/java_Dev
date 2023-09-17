package com.laptop.code.laptop.sessionfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.code.laptop.entity.NewUserEntity;
import com.laptop.code.laptop.pojo.BranchPojo;
import com.laptop.code.laptop.pojo.StaticuserId;
import com.laptop.code.laptop.repository.NewUserRepository;
import com.laptop.code.laptop.service.LTService;
import com.laptop.code.laptop.util.CommonFunction;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Order
@Component
public class SessionFilter implements Filter {

    @Autowired
    LTService ltService;

    @Autowired
    NewUserRepository newUserRepository;

    private void EnableCORSrequestforMobileAPI(HttpServletRequest request, HttpServletResponse response) {
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId,Set-Cookie,Cookie");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods",
                "GET,PUT,POST,DELETE,PATCH,OPTIONS");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");

    }

    public void setCorsConfig(ServletRequest req, ServletResponse res){
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers",
//                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","GET,PUT,POST,DELETE,PATCH,OPTIONS");
//        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        setCorsConfig(request,response);
        System.out.println("Enters Filter Method");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse) response;
        this.EnableCORSrequestforMobileAPI(req,res);
        Optional<NewUserEntity> user=null;
        String URI=req.getRequestURI();
        System.out.println(URI+" uri");
        System.out.println("method"+req.getMethod());
        if("OPTIONS".equals(req.getMethod())){
            System.out.println("filtered");
            chain.doFilter(request,response);
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            System.out.println(cookies.toString()+"session filter cookies ---erhgrbugy---");
            for (int i = 0; i < cookies.length; i++) {
                System.out.println("cooies val --> " +cookies[i].getName());
            }
        }else{
            System.out.println("no cookies found");
        }

        if(!URI.toLowerCase().contains("/login") && !URI.equals("/forgot/password") && !URI.contains("/verify/otp") && !URI.contains("/newPassword") ){
            if(!userkeyValidation(req,res)) {
                //((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FOUND);//302
                ObjectMapper obj = new ObjectMapper();
                JSONObject json =new JSONObject();
                json.put("message","Session expired");
                res.getWriter().write(obj.writeValueAsString(json));
                res.addHeader("Content-Type", "application/json");
                return;
            }
        }
//        else
//            user=ltService.getUser(req.getParameter("userId"));
//
//        String pwd="";
//        String permission="";
//        if(user !=null && user.isPresent() && user.get().getPwd()!=null ){
//            pwd=user.get().getPwd();
//            permission=user.get().getBranch();
//            BranchPojo.branch=user.get().getBranch();
//            System.out.println("Branch "+BranchPojo.branch);
//            System.out.println("permission "+permission);
//        }
//
//        if(req.getRequestURI().contains("/createNewUser") && "LSINJ".equals(permission) && req.getParameter("pwd").equals(pwd) || req.getRequestURI().contains("/deleteUser")  && req.getParameter("pwd").equals(pwd) || req.getRequestURI().contains("/getAllUser")  && req.getParameter("pwd").equals(pwd)  ){
//            System.out.println("admin");
//            chain.doFilter(request,response);
//        }
        chain.doFilter(request,response);
    }

    public boolean userkeyValidation(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        boolean stat=false;


        String  userId = CommonFunction.getUserId(request);
        String userKey = CommonFunction.getUserKey(request);
        System.out.println("userKey "+userKey);
        Optional<NewUserEntity> dbResponse = newUserRepository.findById(userId);
        if(userId == null || userKey == null) {
            session.invalidate();
            return false;
        }
        if (!dbResponse.isEmpty() && userKey.equals(dbResponse.get().getUserKey())) {

            BranchPojo.adminAccess=dbResponse.get().getAdminAccess();
            System.out.println("enters validation");
            stat=true;
        }
        else{
            System.out.println("cookies ");
            Cookie userIdCookieRemove = new Cookie("userId", "");

            response.addCookie(userIdCookieRemove);

            Cookie userKeyCookieRemove = new Cookie("userKey", "");

            response.addCookie(userKeyCookieRemove);
            return false;
        }
        return stat;
    }

}
