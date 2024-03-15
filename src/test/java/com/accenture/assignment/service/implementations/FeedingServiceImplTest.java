package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.dtos.mapper.FeedingMapper;
import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.model.FeedingEntity;
import com.accenture.assignment.data.model.FoodEntity;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.repository.FeedingRepository;
import com.accenture.assignment.data.repository.FoodRepository;
import com.accenture.assignment.data.repository.HorseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedingServiceImplTest {
    //TODO: finish writing tests
    @Mock
    HorseMapper horseMapper;
    @Mock
    FeedingMapper feedingMapper;
    @Mock
    FeedingRepository feedingRepository;
    @Mock
    HorseRepository horseRepository;
    @Mock
    FoodRepository foodRepository;
    @InjectMocks
    FeedingServiceImpl feedingServiceImpl;

//    @Test
//    void testAddHorseToFeeding() {
//        Long feedingId = 1L; Long horseId = 1L;
//        FeedingEntity feedingEntity = FeedingEntity.builder().id(feedingId).build();
//        HorseEntity horseEntity = HorseEntity.builder().id(horseId).build();
//        FeedingEntity feedingEntityUpdated = FeedingEntity.builder().id(feedingId).horse(horseEntity).build();
//
//        when(feedingRepository.findById(feedingId)).thenReturn(Optional.of(feedingEntity));
//        when(horseRepository.findById(horseId)).thenReturn(Optional.of(horseEntity));
//        when(feedingRepository.save(feedingEntityUpdated)).thenReturn(feedingEntityUpdated);
//
//        feedingServiceImpl.addHorseToFeeding(feedingId, horseId);
//
//        verify(feedingRepository).findById(feedingId);
//        verify(horseRepository).findById(horseId);
//        verify(feedingRepository).save(feedingEntityUpdated);
//    }

    @Test
    void testUpdateById() {
        Long foodId = 1L; Long feedingId = 1L; Long horseId = 1L;
        FoodEntity foodEntity = FoodEntity.builder().id(foodId).build();
        HorseEntity horseEntity = HorseEntity.builder().id(horseId).build();
        FeedingDTO feedingDTO = FeedingDTO.builder().id(feedingId).horseId(horseId).foodIds(List.of(foodId)).build();
        FeedingEntity feedingEntity = FeedingEntity.builder().id(feedingId).horse(horseEntity).foods(List.of(foodEntity)).build();
        when(horseRepository.findById(feedingDTO.getId())).thenReturn(Optional.of(horseEntity));
        when(foodRepository.findById(foodId)).thenReturn(Optional.of(foodEntity));
        when(feedingRepository.findById(feedingDTO.getId())).thenReturn(Optional.of(feedingEntity));
        when(feedingRepository.save(feedingEntity)).thenReturn(feedingEntity);
        when(feedingMapper.feedingEntityToDto(feedingEntity)).thenReturn(feedingDTO);

        FeedingDTO returnedFeedingDTO = feedingServiceImpl.updateById(feedingId, feedingDTO);

        assertEquals(feedingDTO, returnedFeedingDTO);
        assertEquals(List.of(foodEntity.getId()), returnedFeedingDTO.getFoodIds());
        assertEquals(horseEntity.getId(), returnedFeedingDTO.getHorseId());
        verify(horseRepository).findById(feedingDTO.getId());
        verify(foodRepository).findById(foodId);
        verify(feedingRepository).findById(feedingDTO.getId());
        verify(feedingRepository).save(feedingEntity);
        verify(feedingMapper).feedingEntityToDto(feedingEntity);
    }

    @Test
    void testCheckHorsesEligibleForFeeding() {
        LocalTime localTime = LocalTime.of(14, 30);
        HorseDTO horseDTO = HorseDTO.builder().build();
        HorseEntity horseEntity = HorseEntity.builder().build();
        FeedingEntity feedingEntity = FeedingEntity.builder().horse(horseEntity).startTime(LocalTime.of(15,0)).build();

        when(feedingRepository.getFeedingsAfterLocalTimeParam(localTime)).thenReturn(List.of(feedingEntity));
        when(horseMapper.horseEntityToDto(feedingEntity.getHorse())).thenReturn(horseDTO);

        List<HorseDTO> horseDTOList = feedingServiceImpl.checkHorsesEligibleForFeeding(localTime);

        assertEquals(List.of(horseDTO), horseDTOList);
        verify(feedingRepository).getFeedingsAfterLocalTimeParam(localTime);
        verify(horseMapper).horseEntityToDto(feedingEntity.getHorse());
    }

    @Test
    void testReleasingFood() {
        UUID uuid = UUID.randomUUID();
        HorseEntity horseEntity = HorseEntity.builder().build();
        FeedingEntity feedingEntity = FeedingEntity.builder().build();

        when(horseRepository.getHorseByUUID(uuid)).thenReturn(horseEntity);
        when(feedingRepository.getFeedingByHorseId(horseEntity.getId())).thenReturn(feedingEntity);
        when(feedingRepository.save(feedingEntity)).thenReturn(feedingEntity);

        feedingServiceImpl.releasingFood(uuid);

        verify(horseRepository).getHorseByUUID(uuid);
        verify(feedingRepository).getFeedingByHorseId(horseEntity.getId());
        verify(feedingRepository).save(feedingEntity);
    }

    @Test
    void testCheckHorsesEligibleForFeedingButNotBeenFed() {
        FeedingEntity feedingEntity = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().build())
                .build();
        when(feedingRepository.getFeedingsBeforeLocalTimeParamAndNotDone(LocalTime.of(10, 0)))
                .thenReturn(List.of(feedingEntity));
        when(horseMapper.horseEntityToDto(HorseEntity.builder().build())).thenReturn(HorseDTO.builder().build());

        List<HorseDTO> returnedHorseDTOList = feedingServiceImpl.checkHorsesEligibleForFeedingButNotBeenFed(
                2,
                LocalTime.of(12, 0));

        assertFalse(returnedHorseDTOList.stream().allMatch(Objects::isNull)); //check if list contains only nulls
        verify(feedingRepository).getFeedingsBeforeLocalTimeParamAndNotDone(LocalTime.of(10, 0));
        verify(horseMapper).horseEntityToDto(HorseEntity.builder().build());
    };

    private static Stream<Arguments>  argumentsStream() {
        return Stream.of(
                Arguments.of(0,2),
                Arguments.of(1,2),
                Arguments.of(2,1),
                Arguments.of(3,0)
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsStream")
    void testCheckHorsesByNumberOfMissedFeedingRanges(Integer number, Integer expected) {
        FeedingEntity feedingEntity1 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(1L).build())
                .build();
        FeedingEntity feedingEntity2 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(6, 0))
                .endTime(LocalTime.of(6, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(1L).build())
                .build();
        FeedingEntity feedingEntity3 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(7, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(2L).build())
                .build();
        LocalTime localTime = LocalTime.of(11,0);
        // first horse with three feedingEntities, missed two of them
        // second horse with one feedingEntitiy, missed it
        when(feedingRepository.getFeedingsBeforeLocalTimeParamAndNotDone(localTime)).thenReturn(List.of(feedingEntity1,feedingEntity2,feedingEntity3));
        when(horseMapper.horseEntityToDto(feedingEntity1.getHorse())).thenReturn(HorseDTO.builder().id(1L).build());
        when(horseMapper.horseEntityToDto(feedingEntity2.getHorse())).thenReturn(HorseDTO.builder().id(1L).build());
        when(horseMapper.horseEntityToDto(feedingEntity3.getHorse())).thenReturn(HorseDTO.builder().id(2L).build());

        List<HorseDTO> returnedList = feedingServiceImpl.checkHorsesByNumberOfMissedFeedingRanges(number,localTime);

        assertEquals(expected,returnedList.size());

        verify(feedingRepository).getFeedingsBeforeLocalTimeParamAndNotDone(localTime);
        verify(horseMapper,times(2)).horseEntityToDto(feedingEntity1.getHorse());
        verify(horseMapper).horseEntityToDto(feedingEntity3.getHorse());
    }

    @Test
    void testCheckHorsesWithNotFinishedFeedings0() {
        FeedingEntity feedingEntity1 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(1L).build())
                .build();
        FeedingEntity feedingEntity2 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(6, 0))
                .endTime(LocalTime.of(6, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(1L).build())
                .build();
        FeedingEntity feedingEntity3 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(7, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(2L).build())
                .build();
        LocalTime localTime = LocalTime.of(11,0);
        when(feedingRepository.getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime)).thenReturn(List.of(feedingEntity1,feedingEntity2,feedingEntity3));
        when(horseMapper.horseEntityToDto(feedingEntity1.getHorse())).thenReturn(HorseDTO.builder().id(1L).build());
        when(horseMapper.horseEntityToDto(feedingEntity2.getHorse())).thenReturn(HorseDTO.builder().id(1L).build());
        when(horseMapper.horseEntityToDto(feedingEntity3.getHorse())).thenReturn(HorseDTO.builder().id(2L).build());

        List<HorseDTO> horseDTOList = feedingServiceImpl.checkHorsesWithNotFinishedFeedings(localTime);

        assertEquals(2,horseDTOList.size());

        verify(feedingRepository).getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime);
        verify(horseMapper,times(2)).horseEntityToDto(feedingEntity1.getHorse());
        verify(horseMapper).horseEntityToDto(feedingEntity3.getHorse());
    }

    private static Stream<Arguments> argumentStream1() {
        return Stream.of(
                Arguments.of(false, false, false, 2),
                Arguments.of(false, true, false, 2),
                Arguments.of(false, true, true, 1),
                Arguments.of(true, true, true, 0)
        );
    }

    @Test
    void testCheckHorsesWithNotFinishedFeedings2() {
        FeedingEntity feedingEntity1 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(1L).build())
                .build();
        FeedingEntity feedingEntity3 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(7, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(2L).build())
                .build();
        LocalTime localTime = LocalTime.of(11,0);
        when(feedingRepository.getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime)).thenReturn(List.of(feedingEntity1,feedingEntity3));
        when(horseMapper.horseEntityToDto(feedingEntity1.getHorse())).thenReturn(HorseDTO.builder().id(1L).build());
        when(horseMapper.horseEntityToDto(feedingEntity3.getHorse())).thenReturn(HorseDTO.builder().id(2L).build());

        List<HorseDTO> horseDTOList = feedingServiceImpl.checkHorsesWithNotFinishedFeedings(localTime);

        assertEquals(2,horseDTOList.size());

        verify(feedingRepository).getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime);
        verify(horseMapper).horseEntityToDto(feedingEntity1.getHorse());
        verify(horseMapper).horseEntityToDto(feedingEntity3.getHorse());
    }

    @Test
    void testCheckHorsesWithNotFinishedFeedings3() {
        FeedingEntity feedingEntity1 = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().id(1L).build())
                .build();

        LocalTime localTime = LocalTime.of(11,0);
        when(feedingRepository.getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime)).thenReturn(List.of(feedingEntity1));
        when(horseMapper.horseEntityToDto(feedingEntity1.getHorse())).thenReturn(HorseDTO.builder().id(1L).build());

        List<HorseDTO> horseDTOList = feedingServiceImpl.checkHorsesWithNotFinishedFeedings(localTime);

        assertEquals(1,horseDTOList.size());

        verify(feedingRepository).getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime);
        verify(horseMapper).horseEntityToDto(feedingEntity1.getHorse());
    }

    @Test
    void testCheckHorsesWithNotFinishedFeedings4() {
        LocalTime localTime = LocalTime.of(11,0);
        when(feedingRepository.getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime)).thenReturn(List.of());

        List<HorseDTO> horseDTOList = feedingServiceImpl.checkHorsesWithNotFinishedFeedings(localTime);

        assertTrue(horseDTOList.isEmpty());

        verify(feedingRepository).getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime);
    }

}
