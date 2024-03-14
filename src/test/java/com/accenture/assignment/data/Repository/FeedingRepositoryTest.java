package com.accenture.assignment.data.Repository;

import com.accenture.assignment.data.model.FeedingEntity;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.repository.FeedingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/schema.sql", "/insert.sql"})
public class FeedingRepositoryTest {
    @Autowired
    private FeedingRepository feedingRepository;
    private FeedingEntity feedingEntity;
    @BeforeEach
    void setUp() {
         feedingEntity = FeedingEntity.builder()
                .id(1L)
                .startWeight(5)
                .endWeight(1)
                .ateAll(false)
                .done(false)
                .startTime(LocalTime.of(10,0))
                .endTime(LocalTime.of(10,30))
                .eatingDuration(Duration.ofMinutes(30))
                .horse(HorseEntity.builder().build())
                .build();
    }
    @Test
    @Transactional
    void testGetFeedingsAfterLocalTimeParam1() {
        // tests when it returns feedingEntity
        LocalTime localTime = LocalTime.of(8,0,0);
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsAfterLocalTimeParam(localTime);

        assertEquals(List.of(feedingEntity),feedingEntityList);
    }

    @Test
    @Transactional
    void testGetFeedingsAfterLocalTimeParam2() {
        // tests when it returns empty list
        LocalTime localTime = LocalTime.of(11,0,0);
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsAfterLocalTimeParam(localTime);

        assertTrue(feedingEntityList.isEmpty());
    }

    @Test
    @Transactional
    void testGetFeedingByHorseId1() {
        // returns feedingEntity
        FeedingEntity returnedFeedingEntity = feedingRepository.getFeedingByHorseId(1L);
        assertEquals(feedingEntity, returnedFeedingEntity);
    }

    @Test
    @Transactional
    void testGetFeedingByHorseId2() {
        // returns null
        FeedingEntity returnedFeedingEntity = feedingRepository.getFeedingByHorseId(2L);
        assertNull(returnedFeedingEntity);
    }


}
