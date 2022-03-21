package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsOpponentsDTO {

    private Integer id;

    private String name;

    private String imageUrl;

    private Integer score;

}
