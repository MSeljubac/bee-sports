package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchStatsPlayerDTO {

    private Integer id;

    private String name;

    private String imageUrl;

    private Integer teamId;

}
