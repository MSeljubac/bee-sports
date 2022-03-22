package io.beesports.mappers;

import io.beesports.domain.dtos.*;
import io.beesports.domain.dtos.responses.MatchDetailsResponseDTO;
import io.beesports.domain.dtos.responses.MatchResponseDTO;
import io.beesports.domain.entities.Match;
import io.beesports.domain.entities.MatchStats;
import io.beesports.domain.entities.Score;
import io.beesports.domain.entities.Team;
import io.beesports.repositories.IMatchRepository;
import io.beesports.repositories.IMatchStatsRepository;
import io.beesports.repositories.IScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MatchMapper {

    private final IScoreRepository scoreRepository;
    private final IMatchStatsRepository matchStatsRepository;
    private final IMatchRepository matchRepository;

    @Autowired
    public MatchMapper(IScoreRepository scoreRepository, IMatchStatsRepository matchStatsRepository, IMatchRepository matchRepository) {
        this.scoreRepository = scoreRepository;
        this.matchStatsRepository = matchStatsRepository;
        this.matchRepository = matchRepository;
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

            match.setSerie(matchDTO.getSerie());

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

            match.setTournament(matchDTO.getTournament());
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
        matchDetailsResponseDTO.setSerieName(match.getSerie().getFullName());
        matchDetailsResponseDTO.setTournamentName(match.getTournament().getName());
        matchDetailsResponseDTO.setTeamOneImageUrl(match.getOpponents().get(0).getImageUrl());
        matchDetailsResponseDTO.setTeamTwoImageUrl(match.getOpponents().get(1).getImageUrl());

        Optional<Score> scoreOne = match.getResults().stream().filter(score -> score.getTeamId().equals(match.getOpponents().get(0).getId())).findFirst();
        matchDetailsResponseDTO.setTeamOneScore(scoreOne.map(Score::getScore).orElse(null));

        Optional<Score> scoreTwo = match.getResults().stream().filter(score -> score.getTeamId().equals(match.getOpponents().get(1).getId())).findFirst();
        matchDetailsResponseDTO.setTeamTwoScore(scoreTwo.map(Score::getScore).orElse(null));

        matchDetailsResponseDTO.setLiveUrl(match.getLiveUrl());

        List<MatchDetailsPlayerStatsDTO> matchDetailsPlayerStatsDTOS = new ArrayList<>();
        List<MatchDetailsTeamStatsDTO> matchDetailsTeamStatsDTOS = new ArrayList<>();
        List<MatchStats> matchStats = matchStatsRepository.getMatchStatsByMatchId(match.getId());
        for (Team opponent : match.getOpponents()) {
            if (!matchStats.isEmpty()) {
                opponent.getPlayers().forEach(player -> {
                    MatchDetailsPlayerStatsDTO playerStatsDTO = new MatchDetailsPlayerStatsDTO();
                    MatchStats playerMatchStats = matchStats.stream().filter(matchStats1 -> matchStats1.getPlayerId().equals(player.getId())).findFirst().orElse(null);

                    MatchStatsPlayerDTO matchStatsPlayerDTO = new MatchStatsPlayerDTO();
                    matchStatsPlayerDTO.setId(player.getId());
                    matchStatsPlayerDTO.setImageUrl(player.getImageUrl());
                    matchStatsPlayerDTO.setName(player.getName());
                    matchStatsPlayerDTO.setTeamId(player.getTeam().getId());
                    playerStatsDTO.setPlayer(matchStatsPlayerDTO);

                    if (playerMatchStats != null) {
                        playerStatsDTO.setKills(playerMatchStats.getKills());
                        playerStatsDTO.setDeaths(playerMatchStats.getDeaths());
                        playerStatsDTO.setAssists(playerMatchStats.getAssists());
                        playerStatsDTO.setWardsPlaced(playerMatchStats.getWardsPlaced());
                        playerStatsDTO.setMinionsKilled(playerMatchStats.getMinionsKilled());
                        playerStatsDTO.setGamesCount(playerMatchStats.getGamesCount());
                    } else {

                    }

                    matchDetailsPlayerStatsDTOS.add(playerStatsDTO);
                });
            }

            List<Match> last3Matches = matchRepository.findTop3ByOpponentsIdAndStatusOrderByScheduledAtDesc(opponent.getId(), "finished");
            if (!last3Matches.isEmpty()) {
                List<MatchStats> teamMatchStats = matchStatsRepository.findAllByMatchIdInAndWardsPlacedNotNull(last3Matches.stream().map(Match::getId).collect(Collectors.toList()));
                if (!teamMatchStats.isEmpty()) {
                    MatchDetailsTeamStatsDTO matchDetailsTeamStatsDTO = new MatchDetailsTeamStatsDTO();
                    matchDetailsTeamStatsDTO.setTeamId(opponent.getId());
                    matchDetailsTeamStatsDTO.setKills(teamMatchStats.stream().mapToDouble(MatchStats::getKills).average().orElse(0));
                    matchDetailsTeamStatsDTO.setDeaths(teamMatchStats.stream().mapToDouble(MatchStats::getDeaths).average().orElse(0));
                    matchDetailsTeamStatsDTO.setAssists(teamMatchStats.stream().mapToDouble(MatchStats::getAssists).average().orElse(0));
                    matchDetailsTeamStatsDTO.setMinionsKilled(teamMatchStats.stream().mapToDouble(MatchStats::getMinionsKilled).average().orElse(0));
                    matchDetailsTeamStatsDTO.setFormPercentage(getFormPercentage(last3Matches, opponent.getId()));
                    matchDetailsTeamStatsDTO.setFormMatches(last3Matches.size());
                    matchDetailsTeamStatsDTO.setWardsPlaced(teamMatchStats.stream().mapToDouble(MatchStats::getWardsPlaced).average().orElse(0));
                    matchDetailsTeamStatsDTO.setOverallScore(getTeamOverallScore(matchDetailsTeamStatsDTO));
                    matchDetailsTeamStatsDTOS.add(matchDetailsTeamStatsDTO);
                }
            }
        }
        matchDetailsResponseDTO.setPlayerStats(matchDetailsPlayerStatsDTOS);
        matchDetailsResponseDTO.setTeamStats(matchDetailsTeamStatsDTOS);
        return matchDetailsResponseDTO;
    }

    private Double getFormPercentage(List<Match> last3Matches, Integer teamId) {
        return last3Matches.stream()
                .filter(match -> match.getWinnerId().equals(teamId))
                .count() / (double) last3Matches.size();
    }

    public MatchResponseDTO entityToResponseDto(Match match) {
        MatchResponseDTO matchResponseDTO = new MatchResponseDTO();
        matchResponseDTO.setId(match.getId());
        matchResponseDTO.setName(match.getName());
        matchResponseDTO.setScheduledAt(match.getScheduledAt());
        matchResponseDTO.setLeagueName(match.getLeague() == null ? null : match.getLeague().getName());
        matchResponseDTO.setSerieName(match.getSerie() == null ? null : match.getSerie().getFullName());
        matchResponseDTO.setTournamentName(match.getTournament() == null ? null : match.getTournament().getName());
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

    private Double getTeamOverallScore(MatchDetailsTeamStatsDTO teamStatsDTO) {
        Double result;

        result = teamStatsDTO.getKills() +
                (teamStatsDTO.getAssists() * 0.5) +
                (teamStatsDTO.getDeaths() * (-0.8)) +
                (teamStatsDTO.getWardsPlaced() * 0.05) +
                (teamStatsDTO.getMinionsKilled() * 0.1);

        return result;
    }

}
