package com.laptop.code.laptop.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CommonFunction {

	public static String getUserId(HttpServletRequest request){
		String userId="";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("userId")) {
					userId = cookies[i].getValue();
				}
			}
		}
		return userId;
	}
    public static String getUserKey(HttpServletRequest request){
        String userKey="";
        Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("userKey")) {
					userKey = cookies[i].getValue();
				}
			}
		}
		return userKey;
    }
}
