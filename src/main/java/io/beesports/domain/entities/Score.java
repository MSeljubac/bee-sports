package io.beesports.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Score {

    @Id
    private UUID id;

    private Integer score;

    @SerializedName("match_id")
    private Integer matchId;

    @SerializedName("team_id")
    private Integer teamId;

}
