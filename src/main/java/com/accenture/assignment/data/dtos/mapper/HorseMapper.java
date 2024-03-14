package com.accenture.assignment.data.dtos.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.accenture.assignment.data.dtos.HorseDTO;
import com.accenture.assignment.data.model.HorseEntity;
import com.accenture.assignment.data.model.OwnerEntity;
import com.accenture.assignment.data.model.StableEntity;
import com.accenture.assignment.service.OwnerService;
import com.accenture.assignment.service.StableService;
import com.accenture.assignment.service.implementations.OwnerServiceImpl;
import com.accenture.assignment.service.implementations.StableServiceImpl;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface HorseMapper {
    HorseDTO horseEntityToDto(HorseEntity horseEntity);
    HorseEntity horseDtoToEntity(HorseDTO horseDTO);




//    @Autowired
//    protected StableService stableService;
//    @Autowired
//    protected OwnerService ownerService;
//    @Mapping(source = "owner", target = "ownerId", qualifiedByName = "ownerToId")
//    @Mapping(source = "stable", target = "stableId", qualifiedByName = "stableToId")
//    public  HorseDTO horseEntityToDTO(HorseEntity horseEntity);
//    @Mapping(source = "stableId", target = "stable", qualifiedByName = "idToStable")
//    @Mapping(source = "ownerId", target = "owner", qualifiedByName = "idToOwner")
//    public abstract HorseEntity horseDTOToEntity(HorseDTO horseDTO);

//    @Named("ownerToId")
//    Long ownerToId(OwnerEntity ownerEntity) {
//        return ownerEntity.getId();
//    }
//    @Named("stableToId")
//    Long stableToId(StableEntity stableEntity) {
//        return stableEntity.getId();
//    }
//    @Named("idToOwner")
//    OwnerEntity idToOwner(Long id) {
//        return ownerService.getEntityById(id);
//    }
//    @Named("idToStable")
//    StableEntity idToStable(Long id) {
//        return stableService.getEntityById(id);
//    }
}
