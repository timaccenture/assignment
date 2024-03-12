package com.accenture.assignment.data.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StableDTO {
    Long id;
    String name;
    String address;
    List<Long> horseIds;


}
