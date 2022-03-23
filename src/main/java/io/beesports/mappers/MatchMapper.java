package io.beesports.mappers;

import io.beesports.domain.dtos.*;
import io.beesports.domain.dtos.responses.MatchDetailsResponseDTO;
import io.beesports.domain.dtos.responses.MatchResponseDTO;
import io.beesports.domain.entities.Match;
import io.beesports.domain.entities.Score;
import io.beesports.domain.entities.Team;
import io.beesports.repositories.IScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MatchMapper {

    private final IScoreRepository scoreRepository;

    @Autowired
    public MatchMapper(IScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Match> dtoToEntityList(List<MatchDTO> matchDTOS) {
        List<Match> resultList = new ArrayList<>();

        matchDTOS.forEach(matchDTO -> {
            Match match = new Match();
            match.setId(matchDTO.getId());
            match.setScheduledAt(matchDTO.getScheduled_at());
            match.setBeginAt(matchDTO.getBegin_at());
            match.setEndAt(matchDTO.getEnd_at());
            match.setLeague(matchDTO.getLeague());
            match.setLiveUrl(matchDTO.getLive_url());
            match.setName(matchDTO.getName());

            List<Team> opponents = new ArrayList<>();
            matchDTO.getOpponents().forEach(opponentsDTO -> {
                opponents.add(opponentsDTO.getOpponent());
            });
            match.setOpponents(opponents);

            List<Score> scores = new ArrayList<>();
            matchDTO.getResults().forEach(scoreDTO -> {
                Score score = scoreRepository.getScoreByMatchAndTeamIds(matchDTO.getId(), scoreDTO.getTeam_id());
                if (score == null) {
                    scores.add(new Score(UUID.randomUUID(), scoreDTO.getScore(), matchDTO.getId(), scoreDTO.getTeam_id()));
                } else {
                    score.setScore(scoreDTO.getScore());
                    scores.add(score);
                }
            });
            match.setResults(scores);
            scoreRepository.saveAll(scores);

            match.setStatus(matchDTO.getStatus());
            match.setWinnerId(matchDTO.getWinner_id());

            resultList.add(match);
        });

        return resultList;
    }

    public MatchDetailsResponseDTO entityToMatchDetails(Match match) {
        MatchDetailsResponseDTO matchDetailsResponseDTO = new MatchDetailsResponseDTO();

        matchDetailsResponseDTO.setDate(match.getScheduledAt());
        matchDetailsResponseDTO.setFullName(match.getName());
        matchDetailsResponseDTO.setLeagueName(match.getLeague().getName());
        matchDetailsResponseDTO.setTeamOneImageUrl(match.getOpponents().get(0).getImageUrl());
        matchDetailsResponseDTO.setTeamTwoImageUrl(match.getOpponents().get(1).getImageUrl());

        Optional<Score> scoreOne = match.getResults().stream().filter(score -> score.getTeamId().equals(match.getOpponents().get(0).getId())).findFirst();
        matchDetailsResponseDTO.setTeamOneScore(scoreOne.map(Score::getScore).orElse(null));

        Optional<Score> scoreTwo = match.getResults().stream().filter(score -> score.getTeamId().equals(match.getOpponents().get(1).getId())).findFirst();
        matchDetailsResponseDTO.setTeamTwoScore(scoreTwo.map(Score::getScore).orElse(null));

        matchDetailsResponseDTO.setLiveUrl(match.getLiveUrl());

        List<MatchDetailsPlayerStatsDTO> matchDetailsPlayerStatsDTOS = new ArrayList<>();
        List<MatchDetailsTeamStatsDTO> matchDetailsTeamStatsDTOS = new ArrayList<>();
        matchDetailsResponseDTO.setPlayerStats(matchDetailsPlayerStatsDTOS);
        matchDetailsResponseDTO.setTeamStats(matchDetailsTeamStatsDTOS);
        return matchDetailsResponseDTO;
    }

    public MatchResponseDTO entityToResponseDto(Match match) {
        MatchResponseDTO matchResponseDTO = new MatchResponseDTO();
        matchResponseDTO.setId(match.getId());
        matchResponseDTO.setName(match.getName());
        matchResponseDTO.setScheduledAt(match.getScheduledAt());
        matchResponseDTO.setLeagueName(match.getLeague() == null ? null : match.getLeague().getName());
        matchResponseDTO.setLiveUrl(match.getLiveUrl());

        List<TeamDTO> teamsList = new ArrayList<>();
        match.getOpponents().forEach(team -> {
            teamsList.add(new TeamDTO(team.getId(), team.getAcronym(), team.getImageUrl(), team.getName()));
        });
        matchResponseDTO.setOpponents(teamsList);

        List<ScoreDTO> scores = new ArrayList<>();
        match.getResults().forEach(score -> {
            scores.add(new ScoreDTO(score.getScore(), score.getTeamId()));
        });
        matchResponseDTO.setResults(scores);

        matchResponseDTO.setStatus(match.getStatus());
        matchResponseDTO.setWinnerId(match.getWinnerId());
        return matchResponseDTO;
    }

}
