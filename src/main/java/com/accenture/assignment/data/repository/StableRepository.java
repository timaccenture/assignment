package com.accenture.assignment.data.repository;

import com.accenture.assignment.data.model.StableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StableRepository extends JpaRepository<StableEntity, Long> {
}
