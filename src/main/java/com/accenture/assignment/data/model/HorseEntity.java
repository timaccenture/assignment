package com.accenture.assignment.data.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class HorseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // use UUID to generate GUID and use it as primary key
    Long id;
    UUID uuid;// GUID
    String name;
    String nickName;
    String breed;
    //TODO: implement OwnerEntity instead of String
    String owner;
    List<LocalTime> feedingRanges;

}
