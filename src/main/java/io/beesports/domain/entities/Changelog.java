package io.beesports.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Changelog {

    @Id
    private UUID id;

    private String description;

    private Date changeDate;

    private String oldValue;

    private String newValue;

    private String endpointUrl;

}
