package com.example.corpCartServer.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.corpCartServer.utils.AppConstants.COOKIE_EXPIRATION_TIME;

public class CookieUtil {

    public static void saveTokenToCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRATION_TIME);
        response.addCookie(cookie);
    }

    public static void clearJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
