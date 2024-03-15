package com.accenture.assignment.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"horse","foods"})
@ToString
@Builder
public class FeedingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Integer startWeight;
    Integer endWeight;
    Boolean ateAll;
    Boolean done;
    LocalTime startTime;
    LocalTime endTime;

    //one horse has many feeding entities
    @ManyToOne
    HorseEntity horse;

    //one feeding entity can have multiple foods and vice versa
    @ManyToMany
    List<FoodEntity> foods;

}
