package com.accenture.assignment.data.dtos;

import com.accenture.assignment.data.model.FoodEntity;
import com.accenture.assignment.data.model.HorseEntity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class FeedingDTO {
    Long id;
    Integer startWeight;
    Integer endWeight;
    Boolean ateAll;
    Boolean done;
    LocalTime startTime;
    LocalTime endTime;
    Long horseId;
    List<Long> foodIds;
}
