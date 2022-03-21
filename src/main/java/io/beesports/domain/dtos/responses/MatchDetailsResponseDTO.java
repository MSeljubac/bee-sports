package io.beesports.domain.dtos.responses;

import io.beesports.domain.dtos.MatchDetailsPlayerStatsDTO;
import io.beesports.domain.dtos.MatchDetailsTeamStatsDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchDetailsResponseDTO {

    private Date date;
    private String fullName;
    private String leagueName;
    private String serieName;
    private String tournamentName;
    private String teamOneImageUrl;
    private String teamTwoImageUrl;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private String liveUrl;

    private List<MatchDetailsPlayerStatsDTO> playerStats;
    private List<MatchDetailsTeamStatsDTO> teamStats;

}
