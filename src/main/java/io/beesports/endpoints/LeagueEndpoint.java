package io.beesports.endpoints;

import io.beesports.domain.dtos.responses.LeagueResponseDTO;
import io.beesports.services.LeagueService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/league")
public class LeagueEndpoint {

    private final LeagueService leagueService;

    @Autowired
    public LeagueEndpoint(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @Operation(summary = "Gets all available leagues.")
    @GetMapping(value = "/all")
    public List<LeagueResponseDTO> getLeagues() {
        return leagueService.getLeagues();
    }

}
