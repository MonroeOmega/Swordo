package com.example.swordo.repository;

import com.example.swordo.models.entity.Monster;
import com.example.swordo.models.entity.MonsterClassEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonsterRepository extends JpaRepository<Monster,Long> {
    Monster findFirstByClasss(MonsterClassEnum classs);
}
