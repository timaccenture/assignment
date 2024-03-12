package com.accenture.assignment.data.repository;

import com.accenture.assignment.data.model.HorseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseRepository extends JpaRepository<HorseEntity,Long> {
}
