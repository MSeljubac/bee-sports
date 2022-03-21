package io.beesports.domain.dtos.matchstats;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsTeamDTO {

    private Integer id;
    private String name;
    private List<MatchStatsTeamPlayerDTO> players;

}
