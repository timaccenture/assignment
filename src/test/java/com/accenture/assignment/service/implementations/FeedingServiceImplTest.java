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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
