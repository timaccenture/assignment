package com.accenture.assignment.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "horses")
@Builder
public class StableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String address;
    @OneToMany(targetEntity = HorseEntity.class, mappedBy = "stable",cascade = CascadeType.ALL, orphanRemoval = true)
    List<HorseEntity> horses;

}
