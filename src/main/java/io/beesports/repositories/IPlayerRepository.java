package io.beesports.repositories;

import io.beesports.domain.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPlayerRepository extends JpaRepository<Player, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM player p " +
            "WHERE p.team_id = ?1")
    List<Player> getTeamPlayers(Integer teamId);

}
