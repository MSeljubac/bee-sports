package io.beesports.mappers;

import io.beesports.domain.dtos.responses.LeagueResponseDTO;
import io.beesports.domain.entities.League;
import org.springframework.stereotype.Component;

@Component
public class LeagueMapper {

    public LeagueResponseDTO entityToDto(League league){
        LeagueResponseDTO leagueResponseDTO = new LeagueResponseDTO();
        leagueResponseDTO.setId(league.getId());
        leagueResponseDTO.setImage_url(league.getImageUrl());
        leagueResponseDTO.setName(league.getName());
        return leagueResponseDTO;
    }

}
