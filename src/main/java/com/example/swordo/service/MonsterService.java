package com.example.swordo.service;

import com.example.swordo.models.entity.Monster;
import com.example.swordo.models.entity.MonsterClassEnum;

import java.util.List;

public interface MonsterService {

    void initMonsters();

    Monster getMonsters(MonsterClassEnum classs);


}
