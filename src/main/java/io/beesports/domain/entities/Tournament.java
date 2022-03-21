package io.beesports.domain.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("begin_at")
    private Date beginAt;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("end_at")
    private Date endAt;

    @ManyToOne
    private League league;

    @Column(length = 2000)
    private String prizepool;

    @Column(length = 2000)
    private String name;

    @SerializedName("winner_id")
    private Integer winnerId;

    @ManyToOne(cascade = CascadeType.ALL)
    private Serie serie;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament", fetch = FetchType.EAGER)
    private List<Match> matches;

}
