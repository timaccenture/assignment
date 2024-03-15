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

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
                .ateAll(true)
                .done(false)
                .startTime(LocalTime.of(10,0))
                .endTime(LocalTime.of(10,30))
                .horse(HorseEntity.builder().build())
                .build();
    }
    @Test
    @Transactional
    void testGetFeedingsAfterLocalTimeParam1() {
        // tests when it returns feedingEntity
        LocalTime localTime = LocalTime.of(8,0,0);
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsAfterLocalTimeParam(localTime);

        assertEquals(3,feedingEntityList.size());
    }

    @Test
    @Transactional
    void testGetFeedingsAfterLocalTimeParam2() {
        // tests when it returns empty list
        LocalTime localTime = LocalTime.of(11,0,0);
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsAfterLocalTimeParam(localTime);

        assertEquals(1,feedingEntityList.size());
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
    void testGetFeedingsBeforeLocalTimeParamAndNotDone1() {
        // returns list of 1
        LocalTime localTime = LocalTime.of(11,0);
        List<FeedingEntity> feedingEntityList= feedingRepository.getFeedingsBeforeLocalTimeParamAndNotDone(localTime);
        assertFalse(feedingEntityList.isEmpty());
        assertEquals(1,feedingEntityList.size());
        assertEquals(false, feedingEntityList.get(0).getDone());
        assertEquals(LocalTime.of(10,0), feedingEntityList.get(0).getStartTime());
    }
    @Test
    @Transactional
    void testGetFeedingsBeforeLocalTimeParamAndNotDone2() {
        // returns list of 2
        LocalTime localTime = LocalTime.of(12,0);
        List<FeedingEntity> feedingEntityList= feedingRepository.getFeedingsBeforeLocalTimeParamAndNotDone(localTime);

        assertFalse(feedingEntityList.isEmpty());
        assertEquals(2,feedingEntityList.size());

        for (FeedingEntity fe: feedingEntityList) assertEquals(false, fe.getDone());

        assertEquals(LocalTime.of(10,0), feedingEntityList.get(0).getStartTime());
        assertEquals(LocalTime.of(11,0), feedingEntityList.get(1).getStartTime());
    }

    @Test
    @Transactional
    void testGetFeedingsBeforeLocalTimeParamAndNotAteAll1() {
        // returns list of 1
        LocalTime localTime = LocalTime.of(11,0);
        List<FeedingEntity> feedingEntityList = feedingRepository.getFeedingsBeforeLocalTimeParamAndNotAteAll(localTime);

        assertFalse(feedingEntityList.isEmpty());
        assertEquals(1, feedingEntityList.size());
        assertEquals(false, feedingEntityList.get(0).getAteAll());
        assertEquals(LocalTime.of(8,0), feedingEntityList.get(0).getStartTime());
    }



}
