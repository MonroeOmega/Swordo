package com.example.swordo.service;

import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import com.example.swordo.views.BattlefieldView;

import java.util.List;

public interface BattlefieldService{

    void initBattlefield();

    Battlefield getBattlefield(BattlefieldSizeEnum size);
    List<Battlefield> getAllBattlefields();
    List<BattlefieldView> viewAllBattlefields();
    BattlefieldView viewBattlefield(Long id);
    Battlefield getBattlefieldById(Long id);
}
