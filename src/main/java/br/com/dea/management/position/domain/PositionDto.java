package br.com.dea.management.position.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PositionDto {
    private Long id;
    private String description;
    private String seniority;

}