package io.beesports.repositories;

import io.beesports.domain.entities.Standing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IStandingRepository extends JpaRepository<Standing, UUID> {

    Standing findByTeamIdAndTournamentId(Integer teamId, Integer tournamentId);

    List<Standing> findAllByTournamentIdInOrderByRank(List<Integer> tournamentIds);

}
