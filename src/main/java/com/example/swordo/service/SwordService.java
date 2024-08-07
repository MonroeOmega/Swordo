package com.example.swordo.service;

import com.example.swordo.models.entity.Sword;

public interface SwordService {
    void saveSword(Sword sword);

    Sword getSword(Long id);

    Sword getBroken();

    void discard(Long id);
}
