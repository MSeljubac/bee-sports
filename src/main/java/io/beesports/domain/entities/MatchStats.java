package io.beesports.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStats {

    @Id
    private UUID id;

    private Integer playerId;

    private Integer teamId;

    private Integer matchId;

    private Double assists;

    private Double deaths;

    private Double kills;

    private Double minionsKilled;

    private Double wardsPlaced;

    private Integer gamesCount;

}
