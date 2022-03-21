package io.beesports.repositories;

import io.beesports.domain.entities.ApiVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IApiVersionRepository extends JpaRepository<ApiVersion, UUID> {
}
