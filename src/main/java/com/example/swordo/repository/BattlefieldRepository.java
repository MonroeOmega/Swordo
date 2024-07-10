package com.example.swordo.repository;

import com.example.swordo.models.entity.Battlefield;
import com.example.swordo.models.entity.BattlefieldSizeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattlefieldRepository extends JpaRepository<Battlefield,Long> {
    Battlefield findFirstBySize(BattlefieldSizeEnum size);
}
