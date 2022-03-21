package io.beesports.repositories;

import io.beesports.domain.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ITeamRepository extends JpaRepository<Team, Integer> {

    @Query(nativeQuery = true, value = "select * from team t " +
            "inner join tournament_teams tt on tt.teams_id = t.id " +
            "inner join tournament tour on tour.id = tt.tournament_id " +
            "inner join serie s on s.id = tour.serie_id " +
            "where s.id = ?1")
    List<Team> findAllBySerieId(Integer serieId);


    @Query(nativeQuery = true, value = "select * from team t " +
            "inner join tournament_teams tt on tt.teams_id = t.id " +
            "inner join tournament tour on tour.id = tt.tournament_id " +
            "where tour.id in ?1")
    List<Team> findAllByTournamentIdsIn(List<Integer> tournamentIds);

}
