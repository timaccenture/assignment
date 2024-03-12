package com.accenture.assignment.data.mapper;

import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.model.HorseEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class HorseMapperTest {

    HorseMapper horseMapper = Mappers.getMapper(HorseMapper.class);

    @Test
    public void testHorseMapperDTOToEntity() {
        //given
        HorseDTO dto = HorseDTO.builder()
                .id(1L)
                .name("flo")
                .owner("owner")
                .build();
        //when
        HorseEntity entity = horseMapper.horseDTOToEntity(dto);
        //then
        assertNotNull(entity);
        assertEquals(dto.getId(),entity.getId());
    }

    @Test
    public void testHorseMapperEntityToDTO() {
        //given
        HorseEntity entity = HorseEntity.builder()
                .id(1L)
                .name("flo")
                .owner("owner")
                .build();
        //when
        HorseDTO dto = horseMapper.horseEntityToDTO(entity);
        //then
        assertNotNull(dto);
        assertEquals(dto.getId(),entity.getId());
    }
}
