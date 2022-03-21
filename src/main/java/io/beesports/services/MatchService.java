package io.beesports.services;

import io.beesports.domain.dtos.responses.MatchDetailsResponseDTO;
import io.beesports.domain.dtos.responses.MatchResponseDTO;
import io.beesports.domain.entities.Match;
import io.beesports.mappers.MatchMapper;
import io.beesports.repositories.IMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final IMatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchService(IMatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
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
}
