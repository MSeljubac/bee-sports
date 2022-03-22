package io.beesports.mappers;

import io.beesports.domain.dtos.MatchStatsDTO;
import io.beesports.domain.dtos.MatchStatsMatchDTO;
import io.beesports.domain.dtos.MatchStatsOpponentsDTO;
import io.beesports.domain.dtos.MatchStatsPlayerDTO;
import io.beesports.domain.dtos.matchstats.MatchStatsStatsDTO;
import io.beesports.domain.dtos.responses.MatchStatsResponseDTO;
import io.beesports.domain.entities.Match;
import io.beesports.domain.entities.MatchStats;
import io.beesports.domain.entities.Score;
import io.beesports.repositories.IMatchStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MatchStatsMapper {

    private final IMatchStatsRepository matchStatsRepository;

    @Autowired
    public MatchStatsMapper(IMatchStatsRepository matchStatsRepository) {
        this.matchStatsRepository = matchStatsRepository;
    }

    public MatchStatsResponseDTO entityListToResponseDto(List<MatchStats> matchStatsList, Match match) {

        MatchStatsResponseDTO resultDto = new MatchStatsResponseDTO();

        MatchStatsMatchDTO matchStatsMatchDTO = new MatchStatsMatchDTO();
        matchStatsMatchDTO.setId(match.getId());
        // matchStatsMatchDTO.setUrl(match.getLiveUrl());
        // matchStatsMatchDTO.setDate(match.getScheduled_at());

        MatchStatsOpponentsDTO teamOneDto = new MatchStatsOpponentsDTO();
        teamOneDto.setId(match.getOpponents().get(0).getId());
        teamOneDto.setImageUrl(match.getOpponents().get(0).getImageUrl());
        teamOneDto.setName(match.getOpponents().get(0).getName());

        Optional<Score> scoreOne = match.getResults().stream().filter(score -> score.getTeamId().equals(match.getOpponents().get(0).getId())).findFirst();
        teamOneDto.setScore(scoreOne.map(Score::getScore).orElse(null));
        //  matchStatsMatchDTO.setTeamOne(teamOneDto);

        MatchStatsOpponentsDTO teamTwoDto = new MatchStatsOpponentsDTO();
        teamTwoDto.setId(match.getOpponents().get(1).getId());
        teamTwoDto.setImageUrl(match.getOpponents().get(1).getImageUrl());
        teamTwoDto.setName(match.getOpponents().get(1).getName());
        Optional<Score> scoreTwo = match.getResults().stream().filter(score -> score.getTeamId().equals(match.getOpponents().get(1).getId())).findFirst();
        teamOneDto.setScore(scoreTwo.map(Score::getScore).orElse(null));
        //  matchStatsMatchDTO.setTeamTwo(teamTwoDto);

        List<MatchStatsDTO> matchStatsDTOS = new ArrayList<>();
        matchStatsList.forEach(matchStats -> {

            MatchStatsDTO matchStatsDTO = new MatchStatsDTO();

            MatchStatsPlayerDTO matchStatsPlayerDTO = new MatchStatsPlayerDTO();
            matchStatsPlayerDTO.setId(matchStats.getPlayerId());
            matchStatsPlayerDTO.setTeamId(matchStats.getTeamId());
            matchStatsPlayerDTO.setName(match.getOpponents().stream()
                    .filter(team -> team.getId().equals(matchStats.getTeamId()))
                    .findFirst().get().getPlayers().stream()
                    .filter(player -> player.getId().equals(matchStats.getPlayerId()))
                    .findFirst().get().getName());
            matchStatsPlayerDTO.setImageUrl(match.getOpponents().stream()
                    .filter(team -> team.getId().equals(matchStats.getTeamId()))
                    .findFirst().get().getPlayers().stream()
                    .filter(player -> player.getId().equals(matchStats.getPlayerId()))
                    .findFirst().get().getImageUrl());

            matchStatsDTO.setPlayer(matchStatsPlayerDTO);

            matchStatsDTO.setDeaths(matchStats.getDeaths());
            matchStatsDTO.setKills(matchStats.getKills());
            matchStatsDTO.setAssists(matchStats.getAssists());
            matchStatsDTO.setMinionsKilled(matchStats.getMinionsKilled());
            matchStatsDTO.setWardsPlaced(matchStats.getWardsPlaced());
            matchStatsDTO.setGamesCount(matchStats.getGamesCount());
            matchStatsDTOS.add(matchStatsDTO);
        });

        resultDto.setMatch(matchStatsMatchDTO);
        resultDto.setStats(matchStatsDTOS);

        return resultDto;

    }

    public MatchStats dtoToEntity(Integer matchId, Integer playerId, Integer teamId, MatchStatsStatsDTO statsDTO) {
        MatchStats matchStats;
        try {
            matchStats = matchStatsRepository.getMatchStatsByPlayerIdAndMatchId(playerId, matchId);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            return null;
        }

        if (matchStats == null) {
            matchStats = new MatchStats();
            matchStats.setId(UUID.randomUUID());
            matchStats.setPlayerId(playerId);
            matchStats.setMatchId(matchId);
            matchStats.setTeamId(teamId);
        }

        matchStats.setAssists(statsDTO.getAverages().getAssists());
        matchStats.setDeaths(statsDTO.getAverages().getDeaths());
        matchStats.setKills(statsDTO.getAverages().getKills());
        matchStats.setMinionsKilled(statsDTO.getAverages().getMinions_killed());
        matchStats.setWardsPlaced(statsDTO.getAverages().getWards_placed());
        matchStats.setGamesCount(statsDTO.getGames_count());

        return matchStats;
    }

}
