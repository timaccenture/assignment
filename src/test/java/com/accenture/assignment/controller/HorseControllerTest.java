package com.accenture.assignment.controller;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.repository.HorseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/schema.sql", "/insert.sql"})
public class HorseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HorseRepository horseRepository;
    @Autowired
    private HorseMapper horseMapper;
    ObjectMapper objectMapper;
    HorseDTO horseDTO;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        horseDTO = HorseDTO.builder().id(5L).ownerId(2L).stableId(1L).build();
    }
    @Test
    void testCreateHorse() throws Exception {
        int horsesCount = horseRepository.findAll().size();
        String json = objectMapper.writeValueAsString(horseDTO);

        mockMvc.perform(post("/horse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        assertEquals(horsesCount+1, horseRepository.findAll().size());
    }
}
