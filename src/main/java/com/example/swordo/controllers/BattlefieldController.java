package com.example.swordo.controllers;

import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.service.BattlefieldService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/battlefield/{id}")
    private String battlefield(Model model, @PathVariable Long id){
        model.addAttribute("battlefield",battlefieldService.getBattlefieldById(id));
        return "battlefield";
    }

}
