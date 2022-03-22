package io.beesports.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Standing {

    @Id
    private UUID id;

    private Integer losses;

    private Integer rank;

    private Integer total;

    private Integer wins;

    @OneToOne
    private Team team;

    @ManyToOne
    private Tournament tournament;

}

