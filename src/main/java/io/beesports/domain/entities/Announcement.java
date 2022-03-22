package io.beesports.domain.entities;

import io.beesports.domain.enums.AnnouncementType;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Announcement {

    @Id
    private UUID id;

    private String content;

    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

}
