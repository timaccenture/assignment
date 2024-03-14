package com.accenture.assignment.data.repository;

import com.accenture.assignment.data.model.FeedingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface FeedingRepository extends JpaRepository<FeedingEntity, Long> {
    @Query("select f from FeedingEntity f where f.startTime > :LocalTimeParam")
    List<FeedingEntity> getFeedingsAfterLocalTimeParam(@Param("LocalTimeParam") LocalTime localTime);
    @Query("select f from FeedingEntity f where f.horse.id = :horse_id")
    FeedingEntity getFeedingByHorseId(@Param("horse_id") Long horseId);

}
