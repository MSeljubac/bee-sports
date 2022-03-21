package io.beesports.domain.dtos.responses;

import io.beesports.domain.dtos.MatchStatsDTO;
import io.beesports.domain.dtos.MatchStatsMatchDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsResponseDTO {

    private MatchStatsMatchDTO match;

    private List<MatchStatsDTO> stats;

}
