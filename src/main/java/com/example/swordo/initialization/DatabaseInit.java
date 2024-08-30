package com.example.swordo.initialization;

import com.example.swordo.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final BattlefieldService battlefieldService;
    private final MonsterService monsterService;
    private final SwordInMakingService swordInMakingService;
    private final BattlefieldMonsterService battlefieldMonsterService;
    private final SwordForgeService swordForgeService;
    private final UserService userService;



    public DatabaseInit(BattlefieldService battlefieldService, MonsterService monsterService, SwordInMakingService swordInMakingService, BattlefieldMonsterService battlefieldMonsterService, SwordForgeService swordForgeService, UserService userService) {
        this.battlefieldService = battlefieldService;
        this.monsterService = monsterService;
        this.swordInMakingService = swordInMakingService;
        this.battlefieldMonsterService = battlefieldMonsterService;
        this.swordForgeService = swordForgeService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        monsterService.initMonsters();
        battlefieldService.initBattlefield();
        swordInMakingService.initSwordsInMaking();
        battlefieldMonsterService.loadFirstMonsters();
        swordForgeService.initSwords();
        userService.generateAdmin();
        //This is used to bring back monsters that are engaged while the app is turned off.
        battlefieldMonsterService.deEngageMonsters();
    }
}
