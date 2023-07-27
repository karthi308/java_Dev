package com.laptop.code.laptop.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CommonFunction {

	public static String getUserId(HttpServletRequest request){
		String userId="";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			System.out.println(cookies.toString()+"these are userid cookies ---erhgrbugy---");
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
			System.out.println(cookies.toString()+"these are appkey cookies ---erhgrbugy---");
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("appKey")) {
					userKey = cookies[i].getValue();
				}
			}
		}
		return userKey;
    }
}
