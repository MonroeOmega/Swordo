package com.example.swordo.service;

import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.BattlefieldSizeEnum;

public interface BattlefieldService{

    void initBattlefield();

    Battlefield getBattlefield(BattlefieldSizeEnum size);

}
