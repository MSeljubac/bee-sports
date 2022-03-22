package io.beesports.domain.dtos.responses;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TournamentResponseDTO {

    private Integer id;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date beginAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;

    private String prizepool;

    private Integer winnerId;

    private Integer serieId;

    private String serieName;

    private Integer leagueId;

    private String leagueName;

    private String leagueImageUrl;
}
