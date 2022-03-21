package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.TeamDetailsResponseDTO;
import io.beesports.domain.dtos.responses.TeamResponseDTO;
import io.beesports.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamEndpoint {

    private final TeamService teamService;

    @Autowired
    public TeamEndpoint(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "Gets all the Teams participating in a Serie.")
    @GetMapping(value = "/serie/{serieId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<TeamResponseDTO> getTeamsBySerie(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                 @PathVariable("serieId") Integer serieId) {
        return teamService.getTeamsBySerie(serieId);
    }

    @Operation(summary = "Gets all the Teams participating in a Tournament.")
    @GetMapping(value = "/tournament/{tournamentIds}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<TeamResponseDTO> getTeamsByTournamentIds(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                         @PathVariable("tournamentIds") String tournamentIds) {
        return teamService.getTeamsByTournamentIds(tournamentIds);
    }

    @Operation(summary = "Gets Team details.")
    @GetMapping(value = "/{teamId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public TeamDetailsResponseDTO getTeamDetails(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                 @PathVariable("teamId") Integer teamId) {
        return teamService.getTeamDetails(teamId);
    }

}
