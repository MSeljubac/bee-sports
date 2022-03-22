package io.beesports.domain.entities;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
