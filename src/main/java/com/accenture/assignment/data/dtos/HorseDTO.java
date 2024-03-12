package com.accenture.assignment.data.dtos;

import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class HorseDTO {
    Long id;
    UUID uuid;// GUID
    String name;
    String nickName;
    String breed;
    String owner;

    List<LocalTime> feedingRanges;
}
