package com.example.swordo.controllers;

import com.example.swordo.exceptions.SwordAlreadyBoughtException;
import com.example.swordo.service.SwordForgeService;
import com.example.swordo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @ExceptionHandler({SwordAlreadyBoughtException.class})
    public ModelAndView handleShopException(SwordAlreadyBoughtException e){
        ModelAndView modelAndView = new ModelAndView("known-errors");
        modelAndView.addObject("reason",e.getMessage());
        return modelAndView;
    }

    @PostMapping("/forge/shop/buy/random")
    public String buyRandom(){
        swordForgeService.buyRandom();
        return "redirect:/town/forge/shop";
    }

    @GetMapping("/forge/upgrade")
    public String upgrade(Model model){
        //Note: Make methods to adjust the prices.
        model.addAttribute("critCost",100);
        model.addAttribute("upgradableCrit", swordForgeService.upgradableCrit());
        model.addAttribute("damageCost",100);
        model.addAttribute("upgradableDamage", swordForgeService.upgradableDamage());
        model.addAttribute("durabilityCost",100);
        model.addAttribute("fixable", swordForgeService.fixable());
        return "forge-upgrade";
    }

    @PostMapping("/forge/upgrade/damage")
    public String upgradeDamage(){
        swordForgeService.upgradeDamage();
        return "redirect:/town/forge/upgrade";
    }

    @PostMapping("/forge/upgrade/crit")
    public String upgradeCrit(){
        swordForgeService.upgradeCrit();
        return "redirect:/town/forge/upgrade";
    }

    @PostMapping("/forge/upgrade/durability")
    public String fix(){
        swordForgeService.fix();
        return "redirect:/town/forge/upgrade";
    }
}
