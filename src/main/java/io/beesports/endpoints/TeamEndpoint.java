package io.beesports.endpoints;

import io.beesports.domain.dtos.responses.TeamDetailsResponseDTO;
import io.beesports.domain.dtos.responses.TeamResponseDTO;
import io.beesports.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(value = "/serie/{serieId}")
    public List<TeamResponseDTO> getTeamsBySerie(@PathVariable("serieId") Integer serieId) {
        return teamService.getTeamsBySerie(serieId);
    }

    @Operation(summary = "Gets all the Teams participating in a Tournament.")
    @GetMapping(value = "/tournament/{tournamentIds}")
    public List<TeamResponseDTO> getTeamsByTournamentIds(@PathVariable("tournamentIds") String tournamentIds) {
        return teamService.getTeamsByTournamentIds(tournamentIds);
    }

    @Operation(summary = "Gets Team details.")
    @GetMapping(value = "/{teamId}")
    public TeamDetailsResponseDTO getTeamDetails(@PathVariable("teamId") Integer teamId) {
        return teamService.getTeamDetails(teamId);
    }

}
