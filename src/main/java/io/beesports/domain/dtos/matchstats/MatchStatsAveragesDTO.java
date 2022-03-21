package io.beesports.domain.dtos.matchstats;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsAveragesDTO {

    private Double assists;
    private Double deaths;
    private Double kills;
    private Double minions_killed;
    private Double wards_placed;

}
