package com.accenture.assignment.service;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.dtos.HorseDTO;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface FeedingService {

    void create(FeedingDTO feedingDTO);
    List<FeedingDTO> findAll();
    FeedingDTO getById(Long id);
    void deleteById(Long id);
    FeedingDTO updateById(Long id, FeedingDTO feedingDTO);

    FeedingDTO addHorseToFeeding(Long feedingId, Long horseId);
    FeedingDTO addFoodToFeeding(Long feedingId, List<Long> foodIds);

    List<HorseDTO> checkHorsesEligibleForFeeding(LocalTime localTime);

    void releasingFood(UUID uuid);

    List<HorseDTO> checkHorsesEligibleForFeedingButNotBeenFed(Integer hours, LocalTime localTime);

    List<HorseDTO> checkHorsesByNumberOfMissedFeedingRanges(Integer number, LocalTime localTime);

    List<HorseDTO> checkHorsesWithNotFinishedFeedings(LocalTime localTime);
}
