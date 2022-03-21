package io.beesports.repositories;

import io.beesports.domain.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IMatchRepository extends JpaRepository<Match, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM match " +
                    "WHERE scheduled_at <= ?2 " +
                    "AND scheduled_at >= ?1")
    List<Match> filterMatches(Date dateFrom, Date dateTo);

    @Query(nativeQuery = true,
            value = "SELECT * FROM \"match\" m  " +
                    "WHERE m.status = 'finished' " +
                    "AND m.id NOT IN (SELECT match_id FROM match_stats)" +
                    "AND m.scheduled_at > now() - interval '16 day'")
    List<Match> getFinishedMatchesWithoutStats();
    List<Match> findTop3ByOpponentsIdAndStatusOrderByScheduledAtDesc(Integer teamId, String status);

}
