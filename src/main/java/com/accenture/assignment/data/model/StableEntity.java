package com.accenture.assignment.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class StableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String address;
    @OneToMany
    @JoinColumn(name = "stable_id")
    List<HorseEntity> horseEntityList;

}
