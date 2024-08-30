package com.example.swordo.controllers;

import com.example.swordo.exceptions.BattlefieldNotFoundException;
import com.example.swordo.exceptions.CheekyException;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.BattlefieldService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/battlefields")
public class BattlefieldController {
    private final BattlefieldService battlefieldService;
    private final BattlefieldMonsterService battlefieldMonsterService;

    public BattlefieldController(BattlefieldService battlefieldService, BattlefieldMonsterService battlefieldMonsterService) {
        this.battlefieldService = battlefieldService;
        this.battlefieldMonsterService = battlefieldMonsterService;
    }

    @GetMapping
    private String battlefields(Model model){
        battlefieldMonsterService.accountForNumbers();
        model.addAttribute("battlefields",battlefieldService.viewAllBattlefields());
        model.addAttribute("whichOneIsHeIn", battlefieldMonsterService.findHisBattlefield());
        battlefieldMonsterService.checkForCheekines();
        return "battlefields";
    }

    @GetMapping("/battlefield/{id}")
    private String battlefield(Model model, @PathVariable Long id){
        model.addAttribute("monsters",battlefieldMonsterService.viewMonsters(id));
        model.addAttribute("battlefieldData",battlefieldService.viewBattlefield(id));
        model.addAttribute("theId", battlefieldMonsterService.checkForHisId());
        battlefieldMonsterService.checkForCheekines();
        return "battlefield";
    }

    @ExceptionHandler({BattlefieldNotFoundException.class})
    private String handleBattlefield(BattlefieldNotFoundException e, Model model){
        model.addAttribute("reason", e.getMessage());
        return "access-errors";
    }

    @ExceptionHandler({CheekyException.class})
    private String handleCheekines(){
        return "redirect:/fight";
    }

}
