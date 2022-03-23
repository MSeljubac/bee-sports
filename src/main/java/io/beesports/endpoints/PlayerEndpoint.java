package io.beesports.endpoints;

import io.beesports.domain.dtos.responses.PlayerResponseDTO;
import io.beesports.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerEndpoint {

    private final PlayerService playerService;

    @Autowired
    public PlayerEndpoint(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Gets player data from player ID.")
    @GetMapping(value = "/{playerId}")
    public PlayerResponseDTO getPlayerDetails(@PathVariable("playerId") Integer playerId) {
        return playerService.getPlayerDetails(playerId);
    }

    @Operation(summary = "Gets all the Players playing in a Team.")
    @GetMapping(value = "/team/{teamId}")
    public List<PlayerResponseDTO> getPlayersByTeam(@PathVariable("teamId") Integer teamId) {
        return playerService.getTeamPlayers(teamId);
    }

}
