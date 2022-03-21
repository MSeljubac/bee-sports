package io.beesports.domain.dtos.responses;

import io.beesports.domain.dtos.ScoreDTO;
import io.beesports.domain.dtos.TeamDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchResponseDTO {

    private Integer id;

    private String name;

    private Date scheduledAt;

    private String leagueName;

    private String serieName;

    private String tournamentName;

    private String liveUrl;

    private List<TeamDTO> opponents;

    private List<ScoreDTO> results;

    private String status;

    private Integer winnerId;

}
