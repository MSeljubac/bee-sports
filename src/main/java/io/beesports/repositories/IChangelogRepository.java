package io.beesports.repositories;

import io.beesports.domain.entities.Changelog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IChangelogRepository extends JpaRepository<Changelog, UUID> {

    List<Changelog> findAllByChangeDateBetween(Date dateFrom, Date dateTo);

}
