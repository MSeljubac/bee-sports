package io.beesports.services;

import io.beesports.domain.dtos.responses.MatchStatsResponseDTO;
import io.beesports.domain.entities.MatchStats;
import io.beesports.mappers.MatchStatsMapper;
import io.beesports.repositories.IMatchRepository;
import io.beesports.repositories.IMatchStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchStatsService {

    private final IMatchStatsRepository matchStatsRepository;
    private final MatchStatsMapper matchStatsMapper;
    private final IMatchRepository matchRepository;

    @Autowired
    public MatchStatsService(IMatchStatsRepository matchStatsRepository, MatchStatsMapper matchStatsMapper,
                             IMatchRepository matchRepository) {
        this.matchStatsRepository = matchStatsRepository;
        this.matchStatsMapper = matchStatsMapper;
        this.matchRepository = matchRepository;
    }

    public MatchStatsResponseDTO getMatchStatsForMatch(Integer matchId) {
        List<MatchStats> matchStatsList = matchStatsRepository.getMatchStatsByMatchId(matchId);
        return matchStatsMapper.entityListToResponseDto(matchStatsList, matchRepository.getOne(matchId));
    }

}
