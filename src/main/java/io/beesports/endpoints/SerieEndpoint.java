package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.SerieResponseDTO;
import io.beesports.services.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serie")
public class SerieEndpoint {

    private final SerieService serieService;

    @Autowired
    public SerieEndpoint(SerieService serieService) {
        this.serieService = serieService;
    }

    @Operation(summary = "Gets all available Series data in a League by League ID.")
    @GetMapping(value = "/{leagueId}", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<SerieResponseDTO> getSeriesByLeague(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                    @PathVariable("leagueId") Integer leagueId) {
        return serieService.getSeriesByLeague(leagueId);
    }

}
