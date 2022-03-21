package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.PlayerResponseDTO;
import io.beesports.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/{playerId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public PlayerResponseDTO getPlayerDetails(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                              @PathVariable("playerId") Integer playerId) {
        return playerService.getPlayerDetails(playerId);
    }

    @Operation(summary = "Gets all the Players playing in a Team.")
    @GetMapping(value = "/team/{teamId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<PlayerResponseDTO> getPlayersByTeam(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                    @PathVariable("teamId") Integer teamId) {
        return playerService.getTeamPlayers(teamId);
    }

}
