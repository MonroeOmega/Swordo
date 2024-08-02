package com.example.swordo.controllers;

import com.example.swordo.service.SwordForgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forge")
public class ForgeController {
    private final SwordForgeService swordForgeService;

    public ForgeController(SwordForgeService swordForgeService) {
        this.swordForgeService = swordForgeService;
    }

    @GetMapping
    public String forge(){
        return "forge";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("forgeSwords",swordForgeService.shopView());
        return "forge-shop";
    }

    @GetMapping("/shop/buy/{id}")
    public String buy(@PathVariable Long id){
        return "index";
    }

    @GetMapping("/shop/buy/random")
    public String buyRandom(){
        return "index";
    }

    @GetMapping("/upgrade")
    public String upgrade(){
        return "forge-upgrade";
    }
}
