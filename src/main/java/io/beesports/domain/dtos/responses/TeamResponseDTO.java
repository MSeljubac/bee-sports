package io.beesports.domain.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamResponseDTO {

    private Integer id;

    private String acronym;

    private String imageUrl;

    private String name;

}
