package io.beesports.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.beesports.domain.dtos.MatchDTO;
import io.beesports.domain.dtos.responses.MatchDetailsResponseDTO;
import io.beesports.domain.dtos.responses.MatchResponseDTO;
import io.beesports.domain.entities.Match;
import io.beesports.domain.enums.MatchType;
import io.beesports.mappers.MatchMapper;
import io.beesports.repositories.ILeagueRepository;
import io.beesports.repositories.IMatchRepository;
import io.beesports.utils.ApiClient;
import io.beesports.utils.BSRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchService {

    private final IMatchRepository matchRepository;
    private final ILeagueRepository leagueRepository;
    private final MatchMapper matchMapper;
    private final ApiClient apiClient;
    private final BSRequestBuilder requestBuilder;

    @Autowired
    public MatchService(IMatchRepository matchRepository, ILeagueRepository leagueRepository, MatchMapper matchMapper, ApiClient apiClient,
                        BSRequestBuilder requestBuilder) {
        this.matchRepository = matchRepository;
        this.leagueRepository = leagueRepository;
        this.matchMapper = matchMapper;
        this.apiClient = apiClient;
        this.requestBuilder = requestBuilder;
    }

    public List<MatchResponseDTO> getMatches(Date dateFrom, Date dateTo, Integer leagueId) {
        List<MatchResponseDTO> responseList = new ArrayList<>();
        List<Match> queryResult = matchRepository.filterMatches(dateFrom, dateTo);

        if (leagueId != null) {
            queryResult = queryResult.stream()
                    .filter(match -> match.getLeague().getId().equals(leagueId))
                    .collect(Collectors.toList());
        }

        queryResult.forEach(match -> {
            responseList.add(matchMapper.entityToResponseDto(match));
        });

        return responseList;
    }

    public MatchDetailsResponseDTO getMatchDetails(Integer matchId) {
        return matchMapper.entityToMatchDetails(matchRepository.getOne(matchId));
    }

    public void fetchMatches(MatchType matchType, int[] leagueIds) throws IOException {
        log.debug("Fetching " + matchType.name() + " matches.");

        int responseSize;
        int totalSize = 0;
        int page = 1;
        do {
            Request request = requestBuilder.buildMatchesRequest(matchType, leagueIds, page);

            String response = apiClient.callApi(request);

            List<MatchDTO> matches = new Gson().fromJson(response, new TypeToken<List<MatchDTO>>() {
            }.getType());

            log.debug("Fetched " + matches.size() + " " + matchType.name() + " matches.");

            List<Match> matchList = new ArrayList<>();
            matches.forEach(matchDTO -> {
                Match match = matchMapper.dtoToEntityList(Collections.singletonList(matchDTO)).get(0);
                match.setLeague(leagueRepository.getOne(matchDTO.getLeague_id()));
                matchList.add(match);
            });
            matchRepository.saveAll(matchList);

            log.debug("Finished persisting " + matches.size() + " " + matchType.name() + " matches.");

            responseSize = matchList.size();
            totalSize += responseSize;
            page++;
        } while (responseSize == 50);
        log.debug("Finished persisting " + totalSize + " " + matchType.name() + " matches in total.");
    }

}
