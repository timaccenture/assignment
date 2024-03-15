package com.accenture.assignment.data.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDTO {
    Long id;
    String name;
    Integer weight;
}
