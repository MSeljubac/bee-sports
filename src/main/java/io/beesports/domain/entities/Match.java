package io.beesports.domain.entities;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("scheduled_at")
    private Date scheduledAt;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("begin_at")
    private Date beginAt;

    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("end_at")
    private Date endAt;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = League.class, fetch = FetchType.EAGER)
    private League league;

    @SerializedName("live_url")
    @Column(length = 2000)
    private String liveUrl;

    @Column(length = 2000)
    private String name;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Team.class)
    private List<Team> opponents;

    @OneToMany
    private List<Score> results;

    @Column(length = 2000)
    private String status;

    @SerializedName("winner_id")
    private Integer winnerId;

}
