package io.beesports.domain.dtos.matchstats;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsStatsDTO {

    private MatchStatsAveragesDTO averages;
    private Integer games_count;

}
