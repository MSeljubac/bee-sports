package io.beesports.domain.dtos.responses;

import io.beesports.domain.enums.AnnouncementType;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnnouncementResponseDTO {

    private UUID id;

    private String content;

    private AnnouncementType type;

    private Date expiryDate;

}
