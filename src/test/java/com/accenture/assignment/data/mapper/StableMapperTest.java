package com.accenture.assignment.data.mapper;

import com.accenture.assignment.data.dtos.mapper.StableMapper;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.dtos.StableDTO;
import com.accenture.assignment.data.model.StableEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StableMapperTest {

    StableMapper stableMapper = Mappers.getMapper(StableMapper.class);

    @Test
    public void testStableMapperDTOToEntity() {
        //given
        StableDTO dto = StableDTO.builder()
                .id(1L)
                .name("Reitschule")
                .build();
        //when
        StableEntity entity = stableMapper.stableDTOToEntity(dto);
        //then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
    }
    @Test
    public void testStableMapperEntityToDTO() {
        //given
        StableEntity entity = StableEntity.builder()
                .id(1L)
                .name("Reitschule")
                .horseEntityList(List.of(HorseEntity.builder().id(1L).name("flo").build()))
                .build();
        //when
        StableDTO dto = stableMapper.stableEntityToDTO(entity);
        //then
        assertNotNull(dto);
        assertEquals(dto.getId(), entity.getId());
        System.out.println(entity.getHorseEntityList().get(0).getId());
        System.out.println(dto.getHorseIds().get(0));
    }
}
