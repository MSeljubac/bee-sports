package io.beesports.repositories;

import io.beesports.domain.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ILeagueRepository extends JpaRepository<League, Integer> {
}
