package com.example.swordo.repository;

import com.example.swordo.models.entity.BattlefieldMonster;
import com.example.swordo.models.entity.MonsterClassEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattlefieldMonsterRepository extends JpaRepository<BattlefieldMonster, Long> {
    void deleteBattlefieldMonsterByMonster_Id(Long id);
    BattlefieldMonster findBattlefieldMonsterByMonster_Id(Long id);
    List<BattlefieldMonster> findAllByEngaged(boolean b);
    int countBattlefieldMonstersByMonster_Classs(MonsterClassEnum classs);
}
