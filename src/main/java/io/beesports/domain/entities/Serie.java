package io.beesports.domain.entities;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Serie {

    @Id
    private Integer id;

    @SerializedName("league_id")
    private Integer leagueId;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("begin_at")
    private Date beginAt;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("end_at")
    private Date endAt;

    @Column(length = 2000)
    private String description;

    @SerializedName("full_name")
    @Column(length = 2000)
    private String fullName;

    @SerializedName("winner_id")
    private Integer winnerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serie")
    private List<Tournament> tournaments;

}
