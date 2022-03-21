package io.beesports.repositories;

import io.beesports.domain.entities.MatchStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMatchStatsRepository extends JpaRepository<MatchStats, Integer> {

    MatchStats getMatchStatsByPlayerIdAndMatchId(Integer playerId, Integer matchId);

    List<MatchStats> getMatchStatsByMatchId(Integer matchId);

    List<MatchStats> findAllByMatchIdInAndWardsPlacedNotNull(List<Integer> matchIds);

}
