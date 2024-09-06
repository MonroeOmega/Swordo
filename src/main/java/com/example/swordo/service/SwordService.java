package com.example.swordo.service;

import com.example.swordo.models.entity.Sword;

import java.util.List;

public interface SwordService {
    void saveSword(Sword sword);

    Sword getBroken();

    void discard(Long id);

    long swordCount();

    void adminDelete(List<Long> ids);

    List<Sword> getAll();
}
