package io.beesports.domain.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsMatchDTO {

    private Integer id;

    private List<MatchStatsOpponentsDTO> opponents;

}
