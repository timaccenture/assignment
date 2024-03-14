package com.accenture.assignment.service;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.FeedingEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface FeedingService {

    void create(FeedingDTO feedingDTO);
    List<FeedingDTO> findAll();
    FeedingDTO getById(Long id);
    void deleteById(Long id);
    FeedingDTO updateById(Long id, FeedingDTO feedingDTO);

//    void addHorseToFeeding(Long feedingId, Long horseId);

    List<HorseDTO> checkHorsesEligibleForFeeding(LocalTime localTime);

    void releasingFood(UUID uuid);

    List<HorseDTO> checkHorsesEligibleForFeedingButNotBeenFed(Duration hours, LocalTime localTime);

    List<HorseDTO> checkHorsesByNumberOfMissedFeedingRanges(Integer number, LocalTime localTime);

    List<HorseDTO> checkHorsesWithNotFinishedFeedings(LocalTime localTime);
}
