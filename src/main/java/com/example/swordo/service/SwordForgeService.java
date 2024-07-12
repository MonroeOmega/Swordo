package com.example.swordo.service;

import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordTypeEnum;

public interface SwordForgeService {
    void forgeSword(SwordTypeEnum type);

    int random(int min, int max);

    void initSwords();

    void addSwordSets(int sets);

    int price();
}
