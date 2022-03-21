package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.MatchStatsResponseDTO;
import io.beesports.services.MatchStatsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class MatchStatsEndpoint {

    private final MatchStatsService matchStatsService;

    @Autowired
    public MatchStatsEndpoint(MatchStatsService matchStatsService) {
        this.matchStatsService = matchStatsService;
    }

    @Operation(summary = "Gets after match stats for all players by match ID.")
    @GetMapping(value = "/match/{matchId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public MatchStatsResponseDTO getMatchStatsForPlayers(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                         @PathVariable("matchId") Integer matchId) {
        return matchStatsService.getMatchStatsForMatch(matchId);
    }

}
