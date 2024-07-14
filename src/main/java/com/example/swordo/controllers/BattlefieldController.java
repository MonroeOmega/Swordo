package com.example.swordo.controllers;

import com.example.swordo.service.BattlefieldService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/battlefields")
public class BattlefieldController {
    private final BattlefieldService battlefieldService;

    public BattlefieldController(BattlefieldService battlefieldService) {
        this.battlefieldService = battlefieldService;
    }

    @GetMapping
    private String battlefields(Model model){
        model.addAttribute("battlefields",battlefieldService.viewAllBattlefields());
        return "battlefields";
    }

}
