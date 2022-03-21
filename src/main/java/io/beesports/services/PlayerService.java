package io.beesports.services;

import io.beesports.domain.dtos.responses.PlayerResponseDTO;
import io.beesports.domain.entities.Player;
import io.beesports.mappers.PlayerMapper;
import io.beesports.repositories.IPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    private final IPlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Autowired
    public PlayerService(IPlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerResponseDTO getPlayerDetails(Integer playerId){
        Player player = playerRepository.getOne(playerId);
        return playerMapper.entityToDto(player);
    }

    public List<PlayerResponseDTO> getTeamPlayers(Integer teamId){
        List<PlayerResponseDTO> responseList = new ArrayList<>();

        List<Player> playerList = playerRepository.getTeamPlayers(teamId);
        playerList.forEach(player -> {
            responseList.add(playerMapper.entityToDto(player));
        });

        return responseList;
    }
}
