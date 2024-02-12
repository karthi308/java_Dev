package com.io.tech.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.service.AuthnService;
import com.io.tech.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order
@Component
public class SessionFilter implements Filter {

    @Autowired
    AuthnService authnService;

    @Autowired
    EnvironmentUtil properties;

    private boolean validateSession(String companyId, String userId, String jwt) {
        String response = authnService.verifyJwt(companyId, userId, jwt);
        return response.contains(Constants.VERIFIED);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        enableCORSRequest(response);
        if ("OPTIONS".equals(request.getMethod()) || isPublicEndpoint(request.getRequestURI()))
            chain.doFilter(servletRequest, servletResponse);
        else
            handleAuthorization(request, response, chain);

    }

    private boolean isPublicEndpoint(String uri) {
        return uri.equals("/authn/login");
    }

    private void handleAuthorization(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String companyId = request.getHeader(Constants.COMPANY_ID);
        String userId = request.getHeader(Constants.USER_ID);
        String jwt = request.getHeader(Constants.AUTHORIZATION);
        String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_NAME);
        if (Validator.hasData(branchName) && Validator.hasData(companyId) && Validator.hasData(userId) && Validator.hasData(jwt) && validateSession(companyId, userId, jwt))
            filterChain.doFilter(request, response);
        else
            setUnAuthorizedException(response);
    }

    private void setUnAuthorizedException(HttpServletResponse http) throws IOException {
        StandardMessageResponse standardMessageResponse = StandardResponseUtil.unAuthorizedResponse();
        ObjectMapper obj = new ObjectMapper();
        http.addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        http.getWriter().write(obj.writeValueAsString(standardMessageResponse));
        http.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    private void enableCORSRequest(HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3008");
        response.setHeader("Access-Control-Allow-Origin", properties.getCrossOriginUrl());
        response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Accept,company-Id,Authorization,user-id,Set-Cookie,Cookie");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

}
