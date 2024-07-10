package com.example.swordo.repository;

import com.example.swordo.models.entity.SwordInMaking;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.service.SwordInMakingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwordInMakingRepository extends JpaRepository<SwordInMaking,Long> {
    SwordInMaking getFirstByType(SwordTypeEnum type);
}
