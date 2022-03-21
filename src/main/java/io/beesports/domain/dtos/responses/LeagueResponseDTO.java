package io.beesports.domain.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeagueResponseDTO {

    private Integer id;

    private String image_url;

    private String name;

}
