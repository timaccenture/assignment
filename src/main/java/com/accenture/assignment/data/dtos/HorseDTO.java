package com.accenture.assignment.data.dtos;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class HorseDTO {
    Long id;
    UUID uuid;// GUID
    String name;
    String nickName;
    String breed;
    LocalDateTime joined;
    Integer feedingsPerDay;
    Duration feedingDuration;
    Long ownerId;
    Long stableId;
}
