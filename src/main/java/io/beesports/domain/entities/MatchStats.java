package io.beesports.domain.entities;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
