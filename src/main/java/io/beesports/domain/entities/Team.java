package io.beesports.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    private Integer id;

    private String acronym;

    @SerializedName("image_url")
    @Column(length = 2000)
    private String imageUrl;

    @Column(length = 2000)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<Player> players;

}
