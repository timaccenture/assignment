package com.accenture.assignment.data.dtos.mapper;

import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.dtos.StableDTO;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.service.HorseService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface StableMapper {
    StableDTO stableEntityToDTO(StableEntity stableEntity);
    StableEntity stableDTOToEntity(StableDTO stableDTO);
//    @Autowired
//    HorseService horseService;
//
//    @Mapping(source = "horseIds", target = "horses", qualifiedByName = "idToHorse")
//    public abstract StableEntity stableDTOToEntity(StableDTO stableDTO);
//
//    @Mapping(source = "horses", target = "horseIds", qualifiedByName = "horseToId")
//    public abstract StableDTO stableEntityToDTO(StableEntity entity);
//
//    @Named("horseToId")
//    Long horseToId(HorseEntity entity) {
//        return entity.getId();
//    }
//    @Named("idToHorse")
//    HorseEntity idToHorse(Long id) {
//        return horseService.getEntityById(id);
//    }
}
