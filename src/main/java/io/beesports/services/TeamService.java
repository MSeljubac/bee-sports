package io.beesports.services;

import io.beesports.domain.dtos.responses.PlayerResponseDTO;
import io.beesports.domain.dtos.responses.TeamDetailsResponseDTO;
import io.beesports.domain.dtos.responses.TeamResponseDTO;
import io.beesports.mappers.TeamMapper;
import io.beesports.repositories.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final ITeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamService(ITeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public List<TeamResponseDTO> getTeamsBySerie(Integer serieId) {
        return teamMapper.entityToDtoList(teamRepository.findAllBySerieId(serieId));
    }

    public List<TeamResponseDTO> getTeamsByTournamentIds(String tournamentIds){
        List<Integer> tournamentIdsList = Arrays.stream(tournamentIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        return teamMapper.entityToDtoList(teamRepository.findAllByTournamentIdsIn(tournamentIdsList));
    }

    public TeamDetailsResponseDTO getTeamDetails(Integer teamId){
        return teamMapper.teamDetailsEntityToDto(teamRepository.getOne(teamId));
    }

}
