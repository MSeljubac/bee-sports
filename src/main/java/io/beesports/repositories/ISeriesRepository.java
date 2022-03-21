package io.beesports.repositories;

import io.beesports.domain.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISeriesRepository extends JpaRepository<Serie, Integer> {

    @Query(nativeQuery = true, value = "select * from serie s " +
            "where s.end_at is null or s.end_at > now()")
    List<Serie> getLatestSeries();

    List<Serie> findAllByLeagueId(Integer leagueId);
}
