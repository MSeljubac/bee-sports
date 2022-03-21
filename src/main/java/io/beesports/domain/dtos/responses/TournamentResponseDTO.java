package io.beesports.domain.dtos.responses;

import com.google.gson.annotations.SerializedName;
import io.beesports.domain.entities.League;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
