package io.beesports.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.beesports.domain.dtos.responses.LeagueResponseDTO;
import io.beesports.domain.entities.League;
import io.beesports.mappers.LeagueMapper;
import io.beesports.repositories.ILeagueRepository;
import io.beesports.utils.ApiClient;
import io.beesports.utils.BSRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LeagueService {

    private final ILeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;
    private final ApiClient apiClient;
    private final BSRequestBuilder requestBuilder;

    @Autowired
    public LeagueService(ILeagueRepository leagueRepository, LeagueMapper leagueMapper, ApiClient apiClient,
                         BSRequestBuilder requestBuilder) {
        this.leagueRepository = leagueRepository;
        this.leagueMapper = leagueMapper;
        this.apiClient = apiClient;
        this.requestBuilder = requestBuilder;
    }

    public List<LeagueResponseDTO> getLeagues() {
        List<LeagueResponseDTO> responseList = new ArrayList<>();

        List<League> leagues = leagueRepository.findAll();
        leagues.forEach(league -> {
            responseList.add(leagueMapper.entityToDto(league));
        });

        return responseList;
    }


    public void fetchLeagues() throws IOException {
        log.debug("Fetching leagues.");

        int responseSize;
        int totalSize = 0;
        int page = 1;
        do {
            Request request = requestBuilder.buildLeaguesRequest(page);

            String response = apiClient.callApi(request);

            List<LeagueResponseDTO> responseList = new Gson().fromJson(response, new TypeToken<List<LeagueResponseDTO>>() {
            }.getType());

            log.debug("Fetched " + responseList.size() + " leagues.");

            List<League> leaguesList = new ArrayList<>();
            responseList.forEach(leagueResponseDTO -> {
                League league = leagueMapper.dtoToEntity(leagueResponseDTO);
                leaguesList.add(league);
            });
            leagueRepository.saveAll(leaguesList);

            log.debug("Persisted " + leaguesList.size() + " leagues.");

            responseSize = leaguesList.size();
            totalSize += responseSize;
            page++;
        } while (responseSize == 50);
        log.debug("Finished persisting " + totalSize + " leagues in total.");
    }

}
