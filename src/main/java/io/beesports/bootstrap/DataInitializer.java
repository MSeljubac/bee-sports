package io.beesports.bootstrap;

import io.beesports.domain.enums.MatchType;
import io.beesports.services.LeagueService;
import io.beesports.services.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final MatchService matchService;
    private final LeagueService leagueService;

    @Value("${lol.leagues.list}")
    private String leaguesList;

    @Value("${pandascore.initial.fetch}")
    private boolean doInitialFetch;

    @Autowired
    public DataInitializer(MatchService matchService, LeagueService leagueService) {
        this.matchService = matchService;
        this.leagueService = leagueService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (doInitialFetch) {
            log.debug("Started data sync on application startup!");

            leagueService.fetchLeagues();

            matchService.fetchMatches(MatchType.PAST, new int[]{});
            matchService.fetchMatches(MatchType.CURRENT, new int[]{});
            matchService.fetchMatches(MatchType.UPCOMING, new int[]{});

            log.debug("Finished data sync on application startup!");
        } else {
            log.debug("Data sync is currently DISABLED.");
        }
    }

}
