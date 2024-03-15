package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.FeedingDTO;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.FeedingEntity;
import com.accenture.assignment.data.repository.FeedingRepository;
import com.accenture.assignment.service.FeedingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/schema.sql","/insert.sql"})
public class FeedingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FeedingRepository feedingRepository;
    @Autowired
    private FeedingService feedingService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testCreateFeeding() throws Exception {
        int feedingCount = feedingRepository.findAll().size();
        FeedingDTO feedingDTO = FeedingDTO.builder().build();
        String json = objectMapper.writeValueAsString(feedingDTO);

        mockMvc.perform(post("/feeding")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        assertEquals(feedingCount+1, feedingRepository.findAll().size());
    }
    private static Stream<Arguments> dataMethod() {
        return Stream.of(
                Arguments.of(LocalTime.of(6,0),2),
                Arguments.of(LocalTime.of(9,0),1)
        );
    }
    @ParameterizedTest
    @MethodSource("dataMethod")
    void testFindHorsesEligibleForFeeding(LocalTime localTime, Integer expected) throws Exception {
        mockMvc.perform(get("/feeding/a/"+localTime))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").value(hasSize(expected)));
    }

    @Test
    @Transactional
    void testReleaseFoodByUUID() throws Exception {
        UUID uuid = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
        mockMvc.perform(put("/feeding/a/{uuid}",uuid))
                .andDo(print())
                .andExpect(status().isOk());

        FeedingEntity feedingEntity = feedingRepository.findById(4L).orElseThrow();
        assertTrue(feedingEntity.getDone());
        assertEquals(uuid,feedingEntity.getHorse().getUuid());
    }

    @Test
    void testFindHorsesEligibleForFeedingButNotBeenFed() throws Exception {
        LocalTime localTime = LocalTime.of(14,0);
        Integer hours = 3;
        mockMvc.perform(get("/feeding/a/done/{localTime}/{hours}", localTime, hours))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").value(hasSize(1)));
    }

    @Test
    void testFindHorsesByNumberOfMissedFeedingRanges1() throws Exception {
        LocalTime localTime = LocalTime.of(13,0);
        Integer number = 2;
        mockMvc.perform(get("/feeding/a/range/{localTime}/{number}",localTime,number))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").value(hasSize(1)));
    }
    @Test
    void testFindHorsesByNumberOfMissedFeedingRanges2() throws Exception {
        LocalTime localTime = LocalTime.of(13,0);
        Integer number = 1;
        mockMvc.perform(get("/feeding/a/range/{localTime}/{number}",localTime,number))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").value(hasSize(2)));
    }

    @Test
    void testFindHorsesWithNotFinishedFeedings() throws Exception {
        LocalTime localTime = LocalTime.of(13,0);
        mockMvc.perform(get("/feeding/a/ateall/{localTime}", localTime))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").value(hasSize(3)));
    }
}
