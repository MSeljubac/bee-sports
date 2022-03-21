package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsDTO {

    private MatchStatsPlayerDTO player;

    private Double assists;

    private Double deaths;

    private Double kills;

    private Double minionsKilled;

    private Double wardsPlaced;

    private Integer gamesCount;

}
