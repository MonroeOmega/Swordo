package com.example.swordo.controllers;

import com.example.swordo.service.SwordForgeService;
import com.example.swordo.service.UserService;
import com.example.swordo.views.AdminUsersView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final SwordForgeService forgeService;

    public AdminController(UserService userService, SwordForgeService forgeService) {
        this.userService = userService;
        this.forgeService = forgeService;
    }

    @GetMapping
    private String admin(Model model){
        model.addAttribute("swordCount",forgeService.swordsInShop());
        return "admin-console";
    }

    @PostMapping("/coins")
    private String coins(){
        userService.adminCoins();
        return "redirect:/admin";
    }

    @PostMapping("/add_swords")
    private String addSwords(){
        forgeService.addSwordSets(1);
        return "redirect:/admin";
    }

    @GetMapping("/users")
    private String users(Model model){
        model.addAttribute("users",userService.adminView());
        return "admin-users";
    }



}
