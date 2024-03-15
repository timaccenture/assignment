package com.accenture.assignment.data.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDTO {
    Long id;
    String name;
    String address;
}
