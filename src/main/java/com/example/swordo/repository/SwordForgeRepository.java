package com.example.swordo.repository;

import com.example.swordo.models.entity.SwordForge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwordForgeRepository extends JpaRepository<SwordForge,Long> {
}
