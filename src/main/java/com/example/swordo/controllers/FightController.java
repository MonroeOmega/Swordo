package com.example.swordo.controllers;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.exceptions.DeathException;
import com.example.swordo.exceptions.MonsterMissingException;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @ExceptionHandler({MonsterMissingException.class})
    private String monsterKilledHandler(MonsterMissingException e,Model model){
        model.addAttribute("reason",e.getMessage());
        return "known-errors";
    }

    @GetMapping()
    private String fight(Model model){
        model.addAttribute("IsItHim",battlefieldMonsterService.checkForHim());
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

    @PostMapping("/strike/him")
    private String strikeHim(){
        userService.strikeHim();
        return "redirect:/fight";
    }

    @ExceptionHandler({DeathException.class})
    private String death(){
        userService.processDeath();
        return "death";
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
