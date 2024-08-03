package com.example.swordo.controllers;

import com.example.swordo.service.SwordForgeService;
import com.example.swordo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/town")
public class TownController {
    private final SwordForgeService swordForgeService;
    private final UserService userService;

    public TownController(SwordForgeService swordForgeService, UserService userService) {
        this.swordForgeService = swordForgeService;
        this.userService = userService;
    }

    @GetMapping
    private String town(){
        return "town";
    }

    @PostMapping("/rest")
    public String rest(){
        userService.rest();
        return "redirect:/town";
    }

    @GetMapping("/forge")
    public String forge(){
        return "forge";
    }

    @GetMapping("/forge/shop")
    public String shop(Model model){
        model.addAttribute("forgeSwords",swordForgeService.shopView());
        return "forge-shop";
    }

    @PostMapping("/forge/shop/buy/{id}")
    public String buy(@PathVariable Long id){
        swordForgeService.buySword(id);
        return "redirect:/town/forge/shop";
    }

    @GetMapping("/forge/shop/buy/random")
    public String buyRandom(){
        return "index";
    }

    @GetMapping("/forge/upgrade")
    public String upgrade(){
        return "forge-upgrade";
    }
}
