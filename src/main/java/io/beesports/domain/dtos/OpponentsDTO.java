package io.beesports.domain.dtos;

import io.beesports.domain.entities.Team;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpponentsDTO {

    private Team opponent;
    private String type;

}
