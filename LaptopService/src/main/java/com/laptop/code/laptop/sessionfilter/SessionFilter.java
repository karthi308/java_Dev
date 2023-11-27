package com.laptop.code.laptop.sessionfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.repository.UserDetailsRepository;
import com.laptop.code.laptop.service.LTService;
import com.laptop.code.laptop.util.CacheUtil;
import com.laptop.code.laptop.util.Constant;
import com.laptop.code.laptop.util.StandardResposneUtil;
import com.laptop.code.laptop.util.Validators;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Order
@Component
public class SessionFilter implements Filter {

    @Autowired
    LTService ltService;

    @Autowired
    UserDetailsRepository newUserRepository;

    @Autowired
    private CacheUtil cache;

    private void enableCORSRequestForMobileAPI(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,Authorization,userId,Set-Cookie,Cookie");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        enableCORSRequestForMobileAPI(req, res);
        String URI = req.getRequestURI();
        System.out.println("method " + req.getMethod());
        if ("OPTIONS".equals(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        if (!URI.toLowerCase().contains("/login") && !URI.equals("/forgot/password") && !URI.contains("/verify/otp") && !URI.contains("/set/password") && !URI.contains("resend/otp")) {
            if (!userValidation(req, res)) {
                //((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FOUND);//302
                ObjectMapper obj = new ObjectMapper();
                res.getWriter().write(obj.writeValueAsString(StandardResposneUtil.unAuthorizedResponse()));
                res.addHeader("Content-Type", "application/json");
//                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                log.error("Error Occurred in Session filter ");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public boolean userValidation(HttpServletRequest request, HttpServletResponse response) {
        boolean stat = false;
        String userId = request.getHeader("userId");
        String userKey = request.getHeader("Authorization");
        if(Validators.hasData(userId) && Validators.hasData(userKey)) {
            if (userKey.startsWith("Bearer ")) {
                userKey = userKey.substring(7);
            }
            System.out.println(userId + " ----- " + userKey);
            Optional<UserDetailsEntity> dbResponse = newUserRepository.findById(userId.toUpperCase());
            if (Validators.hasData(userId) & Validators.hasData(userKey)) {
                if (!dbResponse.isEmpty() && userKey.equals(dbResponse.get().getUserKey()))
                    stat = true;
            }
        }
        return stat;
    }

}
