package com.accenture.assignment.data.Repository;

import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.model.OwnerEntity;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/schema.sql", "/insert.sql"})
public class HorseRepositoryTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HorseRepository horseRepository;
    @Test
    @Transactional
    void testGetHorseByUUID() {
        UUID horseUUID = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
        HorseEntity horseEntity = HorseEntity.builder()
                .id(3L)
                .uuid(horseUUID)
                .name("horse3")
                .nickName("horse3nick")
                .breed("breed3")
                .joined(LocalDate.of(2024,1,1))
                .feedingsPerDay(5)
                .owner(OwnerEntity.builder().id(1L).name("owner1").address("owner1address").build())
                .stable(StableEntity.builder().id(2L).name("stable2").address("stable2address").build())
                .build();
        HorseEntity returnedHorseEntity = horseRepository.getHorseByUUID(horseUUID);
        assertEquals(horseEntity.getId(), returnedHorseEntity.getId());
        assertEquals(horseEntity.getUuid(), returnedHorseEntity.getUuid());
        assertEquals(horseEntity.getName(), returnedHorseEntity.getName());
        assertEquals(horseEntity.getNickName(), returnedHorseEntity.getNickName());
        assertEquals(horseEntity.getJoined(), returnedHorseEntity.getJoined());
        assertEquals(horseEntity.getOwner(), returnedHorseEntity.getOwner());
        assertEquals(horseEntity.getStable(), returnedHorseEntity.getStable());
    }
}
