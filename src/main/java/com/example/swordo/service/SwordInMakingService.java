package com.example.swordo.service;

import com.example.swordo.models.entity.SwordInMaking;
import com.example.swordo.models.entity.SwordTypeEnum;

public interface SwordInMakingService {
    void initSwordsInMaking();

    SwordInMaking getSwordInMakingByType(SwordTypeEnum type);
}
