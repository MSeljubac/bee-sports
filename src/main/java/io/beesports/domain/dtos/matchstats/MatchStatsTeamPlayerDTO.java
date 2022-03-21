package io.beesports.domain.dtos.matchstats;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsTeamPlayerDTO {

    private Integer id;
    private String first_name;
    private String last_name;
    private String name;
    private MatchStatsStatsDTO stats;

}
