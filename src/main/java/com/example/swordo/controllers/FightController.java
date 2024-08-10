package com.example.swordo.controllers;

import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fight")
public class FightController {
    private final BattlefieldMonsterService battlefieldMonsterService;
    private final UserService userService;

    public FightController(BattlefieldMonsterService battlefieldMonsterService, UserService userService) {
        this.battlefieldMonsterService = battlefieldMonsterService;
        this.userService = userService;
    }

    @PostMapping("/{id}")
    private String loadMonster(@PathVariable Long id){
        battlefieldMonsterService.loadCurrentBattlefieldMonsterData(id);
        return "redirect:/fight";
    }

    @GetMapping()
    private String fight(){
        return "fight";
    }

    @PostMapping("/strike")
    private String strike(){
        userService.strike();
        return "redirect:/fight";
    }

    @PostMapping("/strike/windup")
    private String windup(){
        userService.windup();
        return "redirect:/fight";
    }

    @PostMapping("/loot")
    private String loot(){
        userService.loot();
        battlefieldMonsterService.disposeOfCurrentMonster();
        return "redirect:/fight";
    }

    @PostMapping("/bail")
    private String bail(){
        battlefieldMonsterService.returnCurrentMonster();
        return "redirect:/town";
    }
}
