package io.beesports.services;

import io.beesports.domain.dtos.responses.LeagueResponseDTO;
import io.beesports.domain.entities.League;
import io.beesports.mappers.LeagueMapper;
import io.beesports.repositories.ILeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {

    private final ILeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;

    @Autowired
    public LeagueService(ILeagueRepository leagueRepository, LeagueMapper leagueMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueMapper = leagueMapper;
    }

    public List<LeagueResponseDTO> getLeagues(){
        List<LeagueResponseDTO> responseList = new ArrayList<>();

        List<League> leagues = leagueRepository.findAll();
        leagues.forEach(league -> {
            responseList.add(leagueMapper.entityToDto(league));
        });

        return responseList;
    }

}
