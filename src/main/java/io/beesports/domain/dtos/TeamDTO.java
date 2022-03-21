package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDTO {

    private Integer id;

    private String acronym;

    private String imageUrl;

    private String name;

}
