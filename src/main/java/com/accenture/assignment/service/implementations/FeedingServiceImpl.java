package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.dtos.mapper.FeedingMapper;
import com.accenture.assignment.data.dtos.mapper.FoodMapper;
import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.model.FeedingEntity;
import com.accenture.assignment.data.model.FoodEntity;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.repository.FeedingRepository;
import com.accenture.assignment.data.repository.FoodRepository;
import com.accenture.assignment.data.repository.HorseRepository;
import com.accenture.assignment.service.FeedingService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class FeedingServiceImpl implements FeedingService {

    private final FeedingMapper feedingMapper;
    private final HorseMapper horseMapper;
    private final FoodMapper foodMapper;
    private final FeedingRepository feedingRepository;
    private final HorseRepository horseRepository;
    private final FoodRepository foodRepository;


    public FeedingServiceImpl(FeedingMapper feedingMapper, HorseMapper horseMapper, FoodMapper foodMapper, FeedingRepository feedingRepository, HorseRepository horseRepository, FoodRepository foodRepository) {
        this.feedingMapper = feedingMapper;
        this.horseMapper = horseMapper;
        this.foodMapper = foodMapper;
        this.feedingRepository = feedingRepository;
        this.horseRepository = horseRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    public void create(FeedingDTO feedingDTO) {
        //TODO: ateall calculate, times and duration connection
        FeedingEntity feedingEntity = feedingMapper.feedingDtoToEntity(feedingDTO);
        feedingRepository.save(feedingEntity);
    }


    @Override
    public List<FeedingDTO> findAll() {
        List<FeedingEntity> feedings = feedingRepository.findAll();
        return feedings
                .stream()
                .sorted(Comparator.comparing(FeedingEntity::getStartTime))
                .map(feedingMapper::feedingEntityToDto)
                .toList();
    }

    @Override
    public FeedingDTO getById(Long id) {
        FeedingEntity feedingEntity = feedingRepository.findById(id).orElseThrow();
        return feedingMapper.feedingEntityToDto(feedingEntity);
    }

    @Override
    public void deleteById(Long id) {
        feedingRepository.deleteById(id);
    }

    @Override
    public FeedingDTO updateById(Long id, FeedingDTO feedingDTO) {
        HorseEntity horseEntity = horseRepository.findById(feedingDTO.getHorseId()).orElseThrow(RuntimeException::new);
        List<FoodEntity> foodEntities = new ArrayList<>();
        for (Long foodId: feedingDTO.getFoodIds()){
            foodEntities.add(foodRepository.findById(foodId).orElseThrow(RuntimeException::new));
        }
        FeedingEntity feedingEntity = feedingRepository.findById(feedingDTO.getId()).orElseThrow(RuntimeException::new);
        feedingEntity.setFoods(foodEntities);
        feedingEntity.setHorse(horseEntity);
        feedingEntity.setId(id);
        return feedingMapper.feedingEntityToDto(feedingRepository.save(feedingEntity));
    }

//    @Override
//    public void addHorseToFeeding(Long feedingId, Long horseId) {
//        //3. maintaining feeding preferences for horses
//        FeedingEntity feedingEntity = feedingRepository.findById(feedingId).orElseThrow(RuntimeException::new);
//        HorseEntity horseEntity = horseRepository.findById(horseId).orElseThrow(RuntimeException::new);
//        feedingEntity.setHorse(horseEntity);
//        feedingRepository.save(feedingEntity);
//    }


    @Override
    public FeedingDTO addHorseToFeeding(Long feedingId, Long horseId) {
        FeedingEntity feedingEntity = feedingRepository.findById(feedingId).orElseThrow(RuntimeException::new);
        HorseEntity horseEntity = horseRepository.findById(horseId).orElseThrow(RuntimeException::new);
        feedingEntity.setHorse(horseEntity);
        return feedingMapper.feedingEntityToDto(feedingRepository.save(feedingEntity));
    }

    @Override
    public FeedingDTO addFoodToFeeding(Long feedingId, List<Long> foodIds) {
        FeedingEntity feedingEntity = feedingRepository.findById(feedingId).orElseThrow(RuntimeException::new);
        List<FoodEntity> foods = foodIds.stream().map(id -> foodRepository.findById(id).orElseThrow(RuntimeException::new)).collect(Collectors.toList());
        feedingEntity.setFoods(foods);
        System.out.println(foods);
        return feedingMapper.feedingEntityToDto(feedingRepository.save(feedingEntity));
    }

    @Override
    public List<HorseDTO> checkHorsesEligibleForFeeding(LocalTime localTime) {
        //4. checking what horses are eligible for feeding at this time or a manually entered time
        List<FeedingEntity> feedingEntityList = null;
        if (localTime == null) {
            feedingEntityList = feedingRepository.getFeedingsAfterLocalTimeParam(LocalTime.now());
        } else {
            feedingEntityList = feedingRepository.getFeedingsAfterLocalTimeParam(localTime);
        }
        return feedingEntityList
                .stream().map(feedingEntity -> horseMapper.horseEntityToDto(feedingEntity.getHorse())).toList()
                .stream().distinct().toList();
    }

    @Override
    public void releasingFood(UUID uuid) {
        //5. releasing food
        HorseEntity horseEntity = horseRepository.getHorseByUUID(uuid);
        FeedingEntity feedingEntity = feedingRepository.getFeedingByHorseId(horseEntity.getId());
        feedingEntity.setDone(true);
        feedingRepository.save(feedingEntity);
    }

    @Override
    public List<HorseDTO> checkHorsesEligibleForFeedingButNotBeenFed(Integer hours, LocalTime localTime) {
        //6. check if horses have not been fed even when eligible for more than x hours
        LocalTime newLocalTime = localTime.minus(Duration.ofHours(hours));
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsBeforeLocalTimeParamAndNotDone(newLocalTime);
        return feedingEntityList
                .stream().map(feedingEntity -> horseMapper.horseEntityToDto(feedingEntity.getHorse())).toList()
                .stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<HorseDTO> checkHorsesByNumberOfMissedFeedingRanges(Integer number, LocalTime localTime) {
        //7. check horses that have missed their feedingRanges x or more times
        //TODO: implement in a row functionality
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsBeforeLocalTimeParamAndNotDone(localTime);
        List<HorseDTO> horseDTOList = feedingEntityList.stream().map(feedingEntity -> horseMapper.horseEntityToDto(feedingEntity.getHorse())).toList();
        List<HorseDTO> finalList = new ArrayList<>();
        for (HorseDTO horseDTO: horseDTOList) {
            int tmp = Collections.frequency(horseDTOList,horseDTO);
            if (tmp >= number && !finalList.contains(horseDTO)) finalList.add(horseDTO);
        }
        return finalList;
    }

    @Override
    public List<HorseDTO> checkHorsesWithNotFinishedFeedings(LocalTime localTime) {
        //8. check horses that did not complete their meals
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime);
        return feedingEntityList
                .stream().map(feedingEntity -> horseMapper.horseEntityToDto(feedingEntity.getHorse())).toList()
                .stream().distinct().collect(Collectors.toList());
    }
}
