package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.StandingResponseDTO;
import io.beesports.services.StandingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standing")
public class StandingsEndpoint {

    private final StandingService standingService;

    @Autowired
    public StandingsEndpoint(StandingService standingService) {
        this.standingService = standingService;
    }

    @Operation(summary = "Gets current Standings for a Tournament by Tournament ID.")
    @GetMapping(value = "/tournament/{tournamentId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<StandingResponseDTO> getStandingsByTournament(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                              @PathVariable("tournamentId") String tournamentId) {
        return standingService.getStandingsByTournament(tournamentId);
    }

}
