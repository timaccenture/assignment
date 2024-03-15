package com.accenture.assignment.data.dtos;

import lombok.*;

import java.time.LocalDateTime;
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
    Long ownerId;
    Long stableId;
}
