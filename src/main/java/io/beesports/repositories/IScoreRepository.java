package io.beesports.repositories;

import io.beesports.domain.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IScoreRepository extends JpaRepository<Score, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM score s " +
            "WHERE s.match_id = ?1 " +
            "AND s.team_id = ?2")
    Score getScoreByMatchAndTeamIds(Integer matchId, Integer teamId);

}
