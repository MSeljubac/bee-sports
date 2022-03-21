package io.beesports.domain.dtos.responses;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StandingResponseDTO {

    private UUID id;

    private Integer losses;

    private Integer rank;

    private Integer total;

    private Integer wins;

    private Integer tournamentId;

    private String tournamentName;

    private String serieName;

    private String leagueName;

    private TeamResponseDTO team;

}
