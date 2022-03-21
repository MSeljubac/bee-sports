package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.TournamentResponseDTO;
import io.beesports.services.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournament")
public class TournamentEndpoint {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentEndpoint(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Operation(summary = "Gets all Tournaments for a League by League ID.")
    @GetMapping(value = "/league/{leagueId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<TournamentResponseDTO> getTournamentsByLeague(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                              @PathVariable("leagueId") Integer leagueId) {
        return tournamentService.getTournamentsByLeagueId(leagueId);
    }

    @Operation(summary = "Gets all Tournaments for a Serie by Serie ID.")
    @GetMapping(value = "/serie/{serieId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<TournamentResponseDTO> getTournamentsBySerie(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                             @PathVariable("serieId") Integer serieId) {
        return tournamentService.getTournamentsBySerieId(serieId);
    }

    @Operation(summary = "Gets all active Tournaments.")
    @GetMapping(value = "/active", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<TournamentResponseDTO> getAllActiveTournaments(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion) {
        return tournamentService.getAllActiveTournaments();
    }

}
