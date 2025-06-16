package com.example.corpCartServer.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.corpCartServer.utils.AppConstants.COOKIE_EXPIRATION_TIME;

public class CookieUtil {

    public static void saveTokenToCookie(String token, HttpServletResponse response) {
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String cookieValue = String.format("jwt=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=Strict", encodedToken, COOKIE_EXPIRATION_TIME);
        response.setHeader("Set-Cookie", cookieValue);
    }

    public static void clearJwtCookie(HttpServletResponse response) {
        String cookieValue = "jwt=; Max-Age=0; Path=/; Secure; HttpOnly; SameSite=Strict";
        response.setHeader("Set-Cookie", cookieValue);
    }
}
