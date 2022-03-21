package io.beesports.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StandingDTO {

    private Integer losses;
    private Integer rank;
    private Integer total;
    private Integer wins;
    private TeamDTO team;

}
