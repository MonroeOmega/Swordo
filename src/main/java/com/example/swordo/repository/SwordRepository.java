package com.example.swordo.repository;

import com.example.swordo.models.entity.Sword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwordRepository extends JpaRepository<Sword,Long> {
    Sword findFirstById(Long id);
}
