package com.accenture.assignment.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class HorseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // use UUID to generate GUID and use it as primary key
    Long id;
    UUID uuid;// GUID
    String name;
    String nickName;
    String breed;
    LocalDate joined;
    Integer feedingsPerDay;
    //one owner can own many horses, but one horse cannot have many owners
    @ManyToOne(fetch = FetchType.LAZY)
    OwnerEntity owner;
    //one stable has many horses, but one horse cannot be in multiple stables
    @ManyToOne(fetch = FetchType.LAZY)
    StableEntity stable;
}
