package io.beesports.mappers;

import io.beesports.domain.dtos.TeamPlayerDTO;
import io.beesports.domain.dtos.responses.PlayerResponseDTO;
import io.beesports.domain.entities.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerResponseDTO entityToDto(Player player){
        PlayerResponseDTO responseDTO = new PlayerResponseDTO();
        responseDTO.setId(player.getId());
        responseDTO.setFirst_name(player.getFirstName());
        responseDTO.setLast_name(player.getLastName());
        responseDTO.setImage_url(player.getImageUrl());
        responseDTO.setName(player.getName());
        responseDTO.setNationality(player.getNationality());
        responseDTO.setRole(player.getRole());
        return responseDTO;
    }

    public TeamPlayerDTO entityToTeamPLayerDto(Player player){
        TeamPlayerDTO responseDTO = new TeamPlayerDTO();
        responseDTO.setId(player.getId());
        responseDTO.setFirstName(player.getFirstName());
        responseDTO.setLastName(player.getLastName());
        responseDTO.setImageUrl(player.getImageUrl());
        responseDTO.setName(player.getName());
        responseDTO.setNationality(player.getNationality());
        responseDTO.setRole(player.getRole());
        responseDTO.setFlagImageUrl("https://www.countryflags.io/"+ player.getNationality().toLowerCase() +"/flat/64.png");
        return responseDTO;
    }

}
