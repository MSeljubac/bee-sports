package io.beesports.domain.dtos;


import io.beesports.domain.entities.*;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchDTO {

    private Integer id;

    private Date scheduled_at;

    private Date begin_at;

    private Date end_at;

    private League league;

    private Integer league_id;

    private String live_url;

    private String name;

    private List<OpponentsDTO> opponents;

    private Serie serie;

    private Integer serie_id;

    private List<ScoreDTO> results;

    private Tournament tournament;

    private Integer tournament_id;

    private String status;

    private Integer winner_id;

}
