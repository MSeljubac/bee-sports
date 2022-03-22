package io.beesports.domain.dtos.responses;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SerieResponseDTO {

    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date beginAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;

    private String description;

    private String fullName;

    private Integer winnerId;

}
