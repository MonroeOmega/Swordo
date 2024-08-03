package com.example.swordo.controllers;

import com.example.swordo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private String admin(){
        return "admin-console";
    }

    @PostMapping("/coins")
    private String coins(){
        userService.adminCoins();
        return "redirect:/admin";
    }



}
