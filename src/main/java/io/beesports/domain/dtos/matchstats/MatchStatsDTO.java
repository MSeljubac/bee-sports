package io.beesports.domain.dtos.matchstats;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsDTO {

    private List<MatchStatsTeamDTO> teams;

}
