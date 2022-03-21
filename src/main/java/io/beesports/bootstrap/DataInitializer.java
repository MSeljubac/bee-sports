package io.beesports.bootstrap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rometools.rome.io.FeedException;
import io.beesports.domain.dtos.MatchDTO;
import io.beesports.domain.dtos.StandingDTO;
import io.beesports.domain.dtos.matchstats.MatchStatsDTO;
import io.beesports.domain.entities.*;
import io.beesports.mappers.ArticleMapper;
import io.beesports.mappers.MatchMapper;
import io.beesports.mappers.MatchStatsMapper;
import io.beesports.mappers.StandingMapper;
import io.beesports.repositories.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final IPlayerRepository playerRepository;
    private final ITeamRepository teamRepository;
    private final ILeagueRepository leagueRepository;
    private final ISeriesRepository seriesRepository;
    private final IMatchRepository matchRepository;
    private final ITournamentRepository tournamentRepository;
    private final MatchMapper matchMapper;
    private final MatchStatsMapper matchStatsMapper;
    private final IMatchStatsRepository matchStatsRepository;
    private final IStandingRepository standingRepository;
    private final StandingMapper standingMapper;
    private final IArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final RssFetcher rssFetcher;

    @Value("${lol.leagues.list}")
    private String leaguesList;

    @Value("${pandascore.base.url}")
    private String pandascoreBaseUrl;

    @Value("${pandascore.token}")
    private String psToken;

    @Value("${pandascore.initial.fetch}")
    private Boolean doInitialFetch;

    @Value("${pandascore.scheduler}")
    private Boolean schedulerOn;

    private Boolean initialFetchOngoing = Boolean.FALSE;

    @Autowired
    public DataInitializer(IPlayerRepository playerRepository, ITeamRepository teamRepository,
                           ILeagueRepository leagueRepository, ISeriesRepository seriesRepository,
                           IMatchRepository matchRepository, ITournamentRepository tournamentRepository,
                           MatchMapper matchMapper, MatchStatsMapper matchStatsMapper,
                           IMatchStatsRepository matchStatsRepository, IStandingRepository standingRepository,
                           StandingMapper standingMapper, IArticleRepository articleRepository,
                           ArticleMapper articleMapper, RssFetcher rssFetcher) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.seriesRepository = seriesRepository;
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.matchMapper = matchMapper;
        this.matchStatsMapper = matchStatsMapper;
        this.matchStatsRepository = matchStatsRepository;
        this.standingRepository = standingRepository;
        this.standingMapper = standingMapper;
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.rssFetcher = rssFetcher;
    }

    @Override
    public void run(String... args) throws Exception {
        if (doInitialFetch) {
            initialFetchOngoing = Boolean.TRUE;
            log.debug("STARTING data refresh on application startup!");

            if (leagueRepository.count() == 0 || seriesRepository.count() == 0) {
                fetchLeaguesAndSeries();
            }

            if (tournamentRepository.count() == 0 || teamRepository.count() == 0 || matchRepository.count() == 0) {
                fetchTournamentsForSeries(seriesRepository.getLatestSeries());
            }

            if (playerRepository.count() == 0) {
                fetchPlayersForTeams(teamRepository.findAll());
            }

            if (matchRepository.count() == 0) {
                fetchMatchesForSeries(seriesRepository.getLatestSeries());
            }

            List<Match> finishedMatches = matchRepository.getFinishedMatchesWithoutStats();
            log.debug("Found " + finishedMatches.size() + " finished matches!");
            finishedMatches.forEach(match -> {
                fetchPlayerStatsForAMatch(match.getId());
            });

            /*
            if (articleRepository.count() == 0) {
                rssFetcher.readArticlesFromRssFeeds();
            }
            */

            log.debug("ENDING data refresh on application startup!");
            initialFetchOngoing = Boolean.FALSE;
        } else {
            log.debug("Data sync is currently DISABLED.");
        }
    }

    @Scheduled(cron = "0 0/2 * 1/1 * ?")
    public void refreshDataByMinutes() {
        if (schedulerOn && !initialFetchOngoing) {
            log.debug("STARTING data refresh by minutes!");

            List<Serie> latestSeries = seriesRepository.getLatestSeries();
            fetchMatchesForSeries(latestSeries);

            List<Tournament> ongoingTournaments = tournamentRepository.findAllByWinnerIdNullAndEndAtAfter(new Date());
            ongoingTournaments.forEach(tournament -> {
                fetchStandingsForTournament(tournament.getId());
            });

            List<Match> finishedMatches = matchRepository.getFinishedMatchesWithoutStats();
            log.debug("Found " + finishedMatches.size() + " finished matches!");
            finishedMatches.forEach(match -> {
                fetchPlayerStatsForAMatch(match.getId());
            });

            log.debug("ENDING data refresh by minutes!");
        }
    }

    //@Scheduled(cron = "0 0/15 * 1/1 * ?")
    public void refreshDataByQuarter() throws IOException, FeedException {
        if (schedulerOn) {
            rssFetcher.readArticlesFromRssFeeds();
        }
    }

    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void refreshDataByDay() {
        if (schedulerOn && !initialFetchOngoing) {
            log.debug("STARTING daily data refresh!");

            fetchLeaguesAndSeries();
            fetchTournamentsForSeries(seriesRepository.getLatestSeries());
            fetchPlayersForTeams(teamRepository.findAll());

            log.debug("ENDING daily data refresh!");
        }
    }

    private void fetchLeaguesAndSeries() {
        log.debug("Fetching LEAGUES and SERIES!");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getLeaguesListRequestUrl())
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            String responseString = response.body().string();
            if (!responseString.contains("Too many requests")) {
                if (response.code() == 200) {
                    List<League> leagues = new Gson().fromJson(responseString, new TypeToken<List<League>>() {
                    }.getType());
                    log.debug("Fetched " + leagues.size() + " leagues.");
                    leagueRepository.saveAll(leagues);

                    leagues.forEach(league -> {
                        fetchTournamentsForSeries(league.getSeries());
                    });
                }
            } else {
                log.debug("Reached hourly rate limit for PandaScore API - try again after 60 minutes.");
            }
            response.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        log.debug("Finished fetching LEAGUES and SERIES!");
    }

    private void fetchPlayersForTeams(List<Team> teamsList) {
        log.debug("Fetching PLAYERS for " + teamsList.size() + " teams.");

        OkHttpClient client = new OkHttpClient();

        Integer pageNum = 1;
        Integer teamsSize = 0;
        Integer totalPlayers = 0;

        Request teamsRequest = new Request.Builder()
                .url(getTeamPlayersRequestUrl(returnIdsFromTeamList(teamsList), pageNum))
                .build();

        Call teamsCall = client.newCall(teamsRequest);
        try {
            Response teamsResponse = teamsCall.execute();
            String response = teamsResponse.body().string();
            if (!response.contains("Too many requests")) {

                if (teamsResponse.code() == 200) {
                    List<Team> teams = new Gson().fromJson(response, new TypeToken<List<Team>>() {
                    }.getType());
                    log.debug("Fetched " + teams.size() + " teams.");
                    teamRepository.saveAll(teams);

                    for (Team team : teams) {
                        totalPlayers += team.getPlayers().size();
                        List<Player> players = new ArrayList<>();
                        team.getPlayers().forEach(player -> {
                            player.setTeam(team);
                            players.add(player);
                        });
                        playerRepository.saveAll(players);
                    }

                    teamsSize = teams.size();
                    while (teamsSize == 50) {

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        pageNum++;
                        Request teamsRequest2 = new Request.Builder()
                                .url(getTeamPlayersRequestUrl(returnIdsFromTeamList(teamsList), pageNum))
                                .build();

                        Call teamsCall2 = client.newCall(teamsRequest2);
                        try {
                            Response teamsResponse2 = teamsCall2.execute();
                            if (teamsResponse2.code() == 200) {
                                List<Team> teams2 = new Gson().fromJson(teamsResponse2.body().string(), new TypeToken<List<Team>>() {
                                }.getType());
                                log.debug("Fetched " + teams2.size() + " teams.");
                                teamRepository.saveAll(teams2);
                                teamsSize = teams2.size();

                                for (Team team : teams) {
                                    totalPlayers += team.getPlayers().size();
                                    List<Player> players = new ArrayList<>();
                                    team.getPlayers().forEach(player -> {
                                        player.setTeam(team);
                                        players.add(player);
                                    });
                                    playerRepository.saveAll(players);
                                }
                            }
                            teamsResponse2.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                log.debug("Reached hourly rate limit for PandaScore API - try again after 60 minutes.");
            }
            teamsResponse.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        log.debug("Fetched " + totalPlayers + " players.");
        log.debug("Finished fetching PLAYERS for " + teamsList.size() + " teams.");
    }

    @Transactional
    private void fetchTournamentsForSeries(List<Serie> series) {
        log.debug("Fetching TOURNAMENTS for " + series.size() + " series.");

        OkHttpClient client = new OkHttpClient();

        Integer pageNum = 1;
        Integer tournamentsSize = 0;

        Request tournamentRequest = new Request.Builder()
                .url(getTournamentsInSerieRequestUrl(returnIdsFromSerieList(series), 1))
                .build();

        Call tournamentsCall = client.newCall(tournamentRequest);
        try {
            Response tournamentsResponse = tournamentsCall.execute();
            String response = tournamentsResponse.body().string();
            if (!response.contains("Too many requests")) {
                List<Tournament> tournaments = new Gson().fromJson(response, new TypeToken<List<Tournament>>() {
                }.getType());
                log.debug("Fetched " + tournaments.size() + " tournaments.");
                tournamentRepository.saveAll(tournaments);
            /*tournaments.forEach(tournament -> {
                Serie serie = tournament.getSerie();
                if (serie.getTournaments() == null) {
                    serie.setTournaments(tournamentRepository.findAllBySerieId(serie.getId()));
                }
                serie.getTournaments().add(tournament);
                seriesRepository.save(serie);
            });*/
                tournamentsSize = tournaments.size();
                tournamentsResponse.close();
                while (tournamentsSize == 50) {

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    pageNum++;
                    Request tournamentRequest2 = new Request.Builder()
                            .url(getTournamentsInSerieRequestUrl(returnIdsFromSerieList(series), pageNum))
                            .build();

                    Call tournamentsCall2 = client.newCall(tournamentRequest2);
                    try {
                        Response tournaments2 = tournamentsCall2.execute();
                        if (tournaments2.code() == 200) {
                            List<Tournament> tournamentsResponse2 = new Gson().fromJson(tournaments2.body().string(), new TypeToken<List<Tournament>>() {
                            }.getType());
                            log.debug("Fetched " + tournamentsResponse2.size() + " tournaments.");
                            tournamentRepository.saveAll(tournamentsResponse2);
                       /* tournaments.forEach(tournament -> {
                            Serie serie = tournament.getSerie();
                            if (serie.getTournaments() == null) {
                                serie.setTournaments(tournamentRepository.findAllBySerieId(serie.getId()));
                            }
                            serie.getTournaments().add(tournament);
                            seriesRepository.save(serie);
                        });*/
                            tournamentsSize = tournamentsResponse2.size();
                        }
                        tournaments2.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                log.debug("Reached hourly rate limit for PandaScore API - try again after 60 minutes.");
            }
            tournamentsResponse.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        log.debug("Finished fetching TOURNAMENTS for " + series.size() + " series.");
    }

    private void fetchMatchesForSeries(List<Serie> series) {
        log.debug("Fetching MATCHES for " + series.size() + " series.");

        OkHttpClient client = new OkHttpClient();

        Integer pageNum = 1;
        Integer matchesSize = 0;

        Request serieMatchesRequest = new Request.Builder()
                .url(getMatchesForASeriesRequestUrl(returnIdsFromSerieList(series), pageNum))
                .build();

        Call matchesCall = client.newCall(serieMatchesRequest);
        try {
            Response seriesMatches = matchesCall.execute();
            String response = seriesMatches.body().string();
            if (!response.contains("Too many requests")) {
                if (seriesMatches.code() == 200) {

                    List<MatchDTO> matches = new Gson().fromJson(response, new TypeToken<List<MatchDTO>>() {
                    }.getType());
                    log.debug("Fetched " + matches.size() + " matches.");

                    List<Match> matchList = new ArrayList<>();
                    matches.forEach(matchDTO -> {
                        Match match = matchMapper.dtoToEntityList(Arrays.asList(matchDTO)).get(0);
                        match.setLeague(leagueRepository.getOne(matchDTO.getLeague_id()));
                        match.setSerie(seriesRepository.getOne(matchDTO.getSerie_id()));
                        match.setTournament(tournamentRepository.getOne(matchDTO.getTournament_id()));
                        matchList.add(match);
                    });
                    matchRepository.saveAll(matchList);

                    matchesSize = matches.size();
                    seriesMatches.close();
                    while (matchesSize == 50) {

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        pageNum++;
                        Request serieMatchesRequest2 = new Request.Builder()
                                .url(getMatchesForASeriesRequestUrl(returnIdsFromSerieList(series), pageNum))
                                .build();

                        Call matchesCall2 = client.newCall(serieMatchesRequest2);
                        try {
                            Response seriesMatches2 = matchesCall2.execute();
                            if (seriesMatches2.code() == 200) {
                                List<MatchDTO> matches2 = new Gson().fromJson(seriesMatches2.body().string(), new TypeToken<List<MatchDTO>>() {
                                }.getType());
                                log.debug("Fetched " + matches2.size() + " matches.");

                                List<Match> matchList2 = new ArrayList<>();
                                matches2.forEach(matchDTO -> {
                                    Match match = matchMapper.dtoToEntityList(Arrays.asList(matchDTO)).get(0);
                                    match.setLeague(leagueRepository.getOne(matchDTO.getLeague_id()));
                                    match.setSerie(seriesRepository.getOne(matchDTO.getSerie_id()));
                                    match.setTournament(tournamentRepository.getOne(matchDTO.getTournament_id()));
                                    matchList2.add(match);
                                });
                                matchRepository.saveAll(matchList2);

                                matchesSize = matches2.size();
                            }
                            seriesMatches2.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            } else {
                log.debug("Reached hourly rate limit for PandaScore API - try again after 60 minutes.");
            }
            seriesMatches.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        log.debug("Finished fetching MATCHES for " + series.size() + " series.");
    }


    private void fetchPlayerStatsForAMatch(Integer matchId) {
        log.debug("Fetching MATCH STATS for match " + matchId + ".");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getPlayerStatsForAMatch(matchId))
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseString = response.body().string();
            if (!responseString.contains("Too many requests")) {
                if (response.code() == 200) {
                    MatchStatsDTO matchStatsDto = new Gson().fromJson(responseString, new TypeToken<MatchStatsDTO>() {
                    }.getType());

                    matchStatsDto.getTeams().forEach(matchStatsTeamDTO -> {
                        matchStatsTeamDTO.getPlayers().forEach(matchStatsTeamPlayerDTO -> {
                            matchStatsRepository.save(matchStatsMapper.dtoToEntity(matchId,
                                    matchStatsTeamPlayerDTO.getId(),
                                    matchStatsTeamDTO.getId(),
                                    matchStatsTeamPlayerDTO.getStats()));
                        });
                    });
                }
            } else {
                log.debug("Reached hourly rate limit for PandaScore API - try again after 60 minutes.");
            }
            response.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        log.debug("Finished fetching MATCH STATS for match " + matchId + ".");
    }

    private void fetchStandingsForTournament(Integer tournamentId) {
        log.debug("Fetching STANDINGS for Tournament " + tournamentId + ".");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getStandingsForATournament(tournamentId))
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseString = response.body().string();
            if (!responseString.contains("Too many requests")) {
                if (response.code() == 200) {
                    List<StandingDTO> standingDTOS = new Gson().fromJson(responseString, new TypeToken<List<StandingDTO>>() {
                    }.getType());

                    standingDTOS.forEach(standingDTO -> {
                        standingRepository.save(standingMapper.dtoToEntity(standingDTO, tournamentRepository.getOne(tournamentId)));
                    });
                }
            } else {
                log.debug("Reached hourly rate limit for PandaScore API - try again after 60 minutes.");
            }
            response.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        log.debug("Finished fetching STANDINGS for Tournament " + tournamentId + ".");
    }

    // UTILS
    private String getLeaguesListRequestUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(pandascoreBaseUrl + "/leagues").newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("filter[name]", leaguesList);
        urlBuilder.addQueryParameter("page", "1");
        urlBuilder.addQueryParameter("per_page", "50");
        return urlBuilder.build().toString();
    }

    private String getTeamPlayersRequestUrl(String teamIds, Integer page) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(pandascoreBaseUrl + "/teams").newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("filter[id]", teamIds);
        urlBuilder.addQueryParameter("page", page.toString());
        urlBuilder.addQueryParameter("per_page", "50");
        return urlBuilder.build().toString();
    }

    private String getTournamentsInSerieRequestUrl(String seriesIds, Integer page) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(pandascoreBaseUrl + "/tournaments").newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("filter[serie_id]", seriesIds);
        urlBuilder.addQueryParameter("page", page.toString());
        urlBuilder.addQueryParameter("per_page", "50");
        return urlBuilder.build().toString();
    }

    private String getMatchesForASeriesRequestUrl(String seriesIds, Integer page) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(pandascoreBaseUrl + "/matches").newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        urlBuilder.addQueryParameter("filter[serie_id]", seriesIds);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -15);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        String rangeScheduledAt = sdf.format(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DAY_OF_YEAR, +31);
        rangeScheduledAt += "," + sdf.format(calendar.getTime());

        urlBuilder.addQueryParameter("range[scheduled_at]", rangeScheduledAt);
        urlBuilder.addQueryParameter("page", page.toString());
        urlBuilder.addQueryParameter("per_page", "50");
        return urlBuilder.build().toString();
    }

    private String getStandingsForATournament(Integer tournamentId) {
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse("https://api.pandascore.co" + "/tournaments/" + tournamentId.toString() + "/standings")
                .newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        return urlBuilder.build().toString();

    }

    private String getPlayerStatsForAMatch(Integer matchId) {
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(pandascoreBaseUrl + "/matches/" + matchId + "/players/stats")
                .newBuilder();
        urlBuilder.addQueryParameter("token", psToken);
        return urlBuilder.build().toString();
    }

    private String returnIdsFromSerieList(List<Serie> series) {
        StringBuilder result = new StringBuilder();
        for (Serie serie : series) {
            result.append(serie.getId().toString()).append(",");
        }
        return result.toString();
    }

    private String returnIdsFromTeamList(List<Team> teams) {
        StringBuilder result = new StringBuilder();
        for (Team team : teams) {
            result.append(team.getId().toString()).append(",");
        }
        return result.toString();
    }

}
