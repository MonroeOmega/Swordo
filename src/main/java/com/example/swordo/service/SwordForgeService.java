package com.example.swordo.service;

import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.views.SwordShopView;

import java.util.List;

public interface SwordForgeService {
    void forgeSword(SwordTypeEnum type);

    void initSwords();

    void addSwordSets(int sets);

    int price(Sword sword);

    List<SwordShopView> shopView();

    void buySword(Long id);

    void buyRandom();

    boolean upgradableCrit();

    boolean upgradableDamage();

    boolean fixable();

    void upgradeDamage();

    void fix();

    void upgradeCrit();

    long swordsInShop();

    void adminSword(SwordTypeEnum swordType);

    long getLooseSwordCount();

    void adminRemoveLooseSwords();
}
