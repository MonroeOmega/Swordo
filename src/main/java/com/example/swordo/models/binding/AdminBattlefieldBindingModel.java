package com.example.swordo.models.binding;

import com.example.swordo.models.entity.BattlefieldSizeEnum;

public class AdminBattlefieldBindingModel {
    private BattlefieldSizeEnum battlefieldSizeEnum;
    public AdminBattlefieldBindingModel() {
    }
    public BattlefieldSizeEnum getBattlefieldSizeEnum() {
        return battlefieldSizeEnum;
    }
    public void setBattlefieldSizeEnum(BattlefieldSizeEnum battlefieldSizeEnum) {
        this.battlefieldSizeEnum = battlefieldSizeEnum;
    }
}
