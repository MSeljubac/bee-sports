package io.beesports.mappers;

import io.beesports.domain.dtos.TeamPlayerDTO;
import io.beesports.domain.dtos.responses.TeamDetailsResponseDTO;
import io.beesports.domain.dtos.responses.TeamResponseDTO;
import io.beesports.domain.entities.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamMapper {

    private final PlayerMapper playerMapper;

    @Autowired
    public TeamMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public List<TeamResponseDTO> entityToDtoList(List<Team> teams) {
        List<TeamResponseDTO> teamResponseDTOList = new ArrayList<>();

        teams.forEach(team -> {
            TeamResponseDTO teamResponseDTO = new TeamResponseDTO();
            teamResponseDTO.setAcronym(team.getAcronym());
            teamResponseDTO.setId(team.getId());
            teamResponseDTO.setImageUrl(team.getImageUrl());
            teamResponseDTO.setName(team.getName());
            teamResponseDTOList.add(teamResponseDTO);
        });

        return teamResponseDTOList;
    }

    public TeamDetailsResponseDTO teamDetailsEntityToDto(Team team){
        TeamDetailsResponseDTO teamResponseDTO = new TeamDetailsResponseDTO();

        teamResponseDTO.setAcronym(team.getAcronym());
        teamResponseDTO.setId(team.getId());
        teamResponseDTO.setImageUrl(team.getImageUrl());
        teamResponseDTO.setName(team.getName());

        List<TeamPlayerDTO> playerDTOList = new ArrayList<>();
        team.getPlayers().forEach(player -> {
            playerDTOList.add(playerMapper.entityToTeamPLayerDto(player));
        });
        teamResponseDTO.setPlayers(playerDTOList);

        return teamResponseDTO;
    }

}
