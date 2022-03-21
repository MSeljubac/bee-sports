package io.beesports.domain.entities;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiVersion {

    @Id
    private UUID id;

    private String apiVersion;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Changelog> changes;

}

