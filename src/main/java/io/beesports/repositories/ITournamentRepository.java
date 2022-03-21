package io.beesports.repositories;

import io.beesports.domain.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ITournamentRepository extends JpaRepository<Tournament, Integer> {

    List<Tournament> findAllByWinnerIdNullAndEndAtAfter(Date now);

    List<Tournament> findAllBySerieId(Integer serieId);

    @Query(nativeQuery = true, value = "select * from tournament t " +
            "inner join serie s on t.serie_id = s.id " +
            "inner join league l on s.league_id = l.id " +
            "where l.id = ?1")
    List<Tournament> fetchAllFromLeagueId(Integer leagueId);

}
