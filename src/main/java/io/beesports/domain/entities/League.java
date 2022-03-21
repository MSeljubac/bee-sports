package io.beesports.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class League {

    @Id
    private Integer id;

    @SerializedName("image_url")
    @Column(length = 2000)
    private String imageUrl;

    @Column(length = 2000)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Serie> series;

}
