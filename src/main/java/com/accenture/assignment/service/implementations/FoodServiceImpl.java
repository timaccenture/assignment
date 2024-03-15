package com.accenture.assignment.service.implementations;

import com.accenture.assignment.data.dtos.FoodDTO;
import com.accenture.assignment.data.dtos.mapper.FeedingMapper;
import com.accenture.assignment.data.dtos.mapper.FoodMapper;
import com.accenture.assignment.data.model.FoodEntity;
import com.accenture.assignment.data.repository.FoodRepository;
import com.accenture.assignment.service.FeedingService;
import com.accenture.assignment.service.FoodService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    public final FoodRepository foodRepository;
    public final FoodMapper foodMapper;

    public FoodServiceImpl(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    @Override
    public void create(FoodDTO foodDTO) {
        FoodEntity foodEntity = foodMapper.foodDTOToEntity(foodDTO);
        foodRepository.save(foodEntity);
    }

    @Override
    public List<FoodDTO> findAll() {
        List<FoodEntity> foodEntities = foodRepository.findAll();
        return foodEntities
                .stream()
                .sorted(Comparator.comparing(FoodEntity::getWeight))
                .map(foodMapper::foodEntityToDTO)
                .toList();
    }

    @Override
    public FoodDTO getById(Long id) {
        FoodEntity foodEntity = foodRepository.findById(id).orElseThrow(RuntimeException::new);
        return foodMapper.foodEntityToDTO(foodEntity);
    }

    @Override
    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public FoodDTO updateById(Long id, FoodDTO foodDTO) {
        FoodEntity foodEntity = foodRepository.findById(foodDTO.getId()).orElseThrow(RuntimeException::new);
        foodEntity.setId(id);
        foodRepository.save(foodEntity);
        return foodMapper.foodEntityToDTO(foodEntity);
    }
}
