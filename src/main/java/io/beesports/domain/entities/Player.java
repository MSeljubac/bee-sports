package io.beesports.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    private Integer id;

    @SerializedName("first_name")
    @Column(length = 2000)
    private String firstName;

    @SerializedName("last_name")
    @Column(length = 2000)
    private String lastName;

    @Column(length = 2000)
    private String name;

    private String nationality;

    private String role;

    @SerializedName("image_url")
    @Column(length = 2000)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Team.class)
    private Team team;

}
