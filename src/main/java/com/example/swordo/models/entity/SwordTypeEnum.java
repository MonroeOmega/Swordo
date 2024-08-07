package com.example.swordo.models.entity;

import java.util.concurrent.ThreadLocalRandom;

public enum SwordTypeEnum {
    DAGGER,SHORTSWORD,LONGSWORD,GREATSWORD,BROKEN_SWORD;

    public static SwordTypeEnum random(){
        return values()[ThreadLocalRandom.current().nextInt(SwordTypeEnum.values().length)];
    }
}
