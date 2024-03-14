package com.accenture.assignment.data.repository;

import com.accenture.assignment.data.model.HorseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HorseRepository extends JpaRepository<HorseEntity,Long> {
    @Query("select f from HorseEntity f where f.uuid = :UUID")
    HorseEntity getHorseByUUID(@Param("UUID") UUID UUID);
}
