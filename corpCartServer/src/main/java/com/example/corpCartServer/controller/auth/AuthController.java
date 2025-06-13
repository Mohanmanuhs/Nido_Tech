package com.example.corpCartServer.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth")
public class AuthController {

    @GetMapping("/sessionExpired")
    public ResponseEntity<String> sessionExpiredPage() {
        return ResponseEntity.ok("your session has expired,please login again");
    }
}
