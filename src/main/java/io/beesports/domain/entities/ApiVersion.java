package io.beesports.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

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

