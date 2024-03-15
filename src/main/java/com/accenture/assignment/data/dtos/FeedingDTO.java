package com.accenture.assignment.data.dtos;

import lombok.*;

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
