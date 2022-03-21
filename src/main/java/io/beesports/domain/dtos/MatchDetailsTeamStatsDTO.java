package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchDetailsTeamStatsDTO {

    private Integer teamId;
    private Double formPercentage;
    private Integer formMatches;
    private Double assists;
    private Double deaths;
    private Double kills;
    private Double minionsKilled;
    private Double wardsPlaced;
    private Double overallScore;

}
