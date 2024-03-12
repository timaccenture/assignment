package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.mapper.HorseMapper;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.repository.HorseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorseServiceImplTest {

    @Mock
    HorseRepository horseRepository;
    HorseMapper horseMapper;
    @InjectMocks
    HorseServiceImpl horseServiceImpl;

    public HorseServiceImplTest() {
        this.horseServiceImpl = new HorseServiceImpl(horseRepository);
        this.horseMapper = Mappers.getMapper(HorseMapper.class);
    }

    @Test
    void testCreate() {
        HorseDTO dto = HorseDTO.builder().id(3L).build();
        HorseEntity entity = horseMapper.horseDTOToEntity(dto);

        when(horseRepository.save(entity)).thenReturn(entity);
        horseServiceImpl.create(dto);

        verify(horseRepository).save(entity);
    }

    @Test
    void testFindAll() {
        //given
        HorseEntity entity = HorseEntity.builder().id(3L).build();

        //when
        when(horseRepository.findAll()).thenReturn(List.of(entity));
        List<HorseDTO> horses = horseServiceImpl.findAll();

        //then
        assertEquals(horses, List.of(horseMapper.horseEntityToDTO(entity)));
        verify(horseRepository).findAll();
    }

    @Test
    void testGetById() {

        HorseEntity entity = HorseEntity.builder().id(3L).build();

        when(horseRepository.findById(3L)).thenReturn(Optional.of(entity));
        HorseDTO dto = horseServiceImpl.getById(3L);

        assertEquals(horseMapper.horseDTOToEntity(dto), entity);
        verify(horseRepository).findById(3L);
    }

    @Test
    void testGetEntityById() {
        HorseEntity entity = HorseEntity.builder().id(3L).build();

        when(horseRepository.findById(3L)).thenReturn(Optional.of(entity));
        HorseEntity returnedEntity = horseServiceImpl.getEntityById(3L);

        assertEquals(entity, returnedEntity);
        verify(horseRepository).findById(3L);
    }

    @Test
    void testDeleteById() {
        //when
        horseServiceImpl.deleteById(3L);
        //then
        verify(horseRepository).deleteById(3L);
    }

    @Test
    void testUpdateById() {
        //given
        Long id = 3L;
        HorseDTO updatedDto = HorseDTO.builder().id(id).build();
        HorseEntity updatedEntity = horseMapper.horseDTOToEntity(updatedDto);
        //when
        when(horseRepository.save(updatedEntity)).thenReturn(updatedEntity);
        HorseDTO result = horseServiceImpl.updateById(id, updatedDto);
        //then
        verify(horseRepository).save(updatedEntity);
        assertEquals(updatedDto, result);
    }


}
