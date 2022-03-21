package io.beesports.domain.dtos.responses;

import io.beesports.domain.dtos.TeamPlayerDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDetailsResponseDTO {

    private Integer id;

    private String acronym;

    private String imageUrl;

    private String name;

    private List<TeamPlayerDTO> players;

}
