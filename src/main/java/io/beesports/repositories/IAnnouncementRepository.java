package io.beesports.repositories;

import io.beesports.domain.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IAnnouncementRepository extends JpaRepository<Announcement, UUID> {


    @Query(nativeQuery = true, value = "SELECT * FROM announcement " +
            "WHERE announcement.expiry_date > ?1")
    List<Announcement> getAllNonExpiredAnnouncements(Date nowDate);

}
