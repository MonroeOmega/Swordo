package com.example.swordo.repository;

import com.example.swordo.models.entity.BattlefieldMonster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattlefieldMonsterRepository extends JpaRepository<BattlefieldMonster, Long> {
}
