package io.beesports.domain.dtos.responses;

import com.google.gson.annotations.SerializedName;
import io.beesports.domain.entities.Tournament;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

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
