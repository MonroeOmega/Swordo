package com.example.swordo.controllers;

import com.example.swordo.service.BattlefieldMonsterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fight")
public class FightController {
    private final BattlefieldMonsterService battlefieldMonsterService;

    public FightController(BattlefieldMonsterService battlefieldMonsterService) {
        this.battlefieldMonsterService = battlefieldMonsterService;
    }

    @GetMapping("/{id}")
    private String loadMonster(@PathVariable Long id){
        battlefieldMonsterService.loadCurrentBattlefieldMonsterData(id);
        return "redirect:/fight";
    }

    @GetMapping()
    private String fight(){
        return "fight";
    }
}
