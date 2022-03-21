package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.MatchDetailsResponseDTO;
import io.beesports.domain.dtos.responses.MatchResponseDTO;
import io.beesports.services.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchEndpoint {

    private final MatchService matchService;

    @Autowired
    public MatchEndpoint(MatchService matchService) {
        this.matchService = matchService;
    }

    @Operation(summary = "Gets scheduled matches by league in time range.", description = "Date format: yyyy-MM-dd")
    @GetMapping(value = "/filter", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<MatchResponseDTO> getMatches(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                             @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                             @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                             @RequestParam(value = "leagueId", required = false) Integer leagueId) {
        return matchService.getMatches(dateFrom, dateTo, leagueId);
    }

    @Operation(summary = "Gets Match Details.")
    @GetMapping(value = "/{matchId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public MatchDetailsResponseDTO getMatchDetails(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                   @PathVariable("matchId") Integer matchId) {
        return matchService.getMatchDetails(matchId);
    }

}
