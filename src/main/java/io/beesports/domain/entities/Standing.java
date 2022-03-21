package io.beesports.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

