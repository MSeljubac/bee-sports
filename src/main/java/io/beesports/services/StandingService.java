package io.beesports.services;

import io.beesports.domain.dtos.responses.StandingResponseDTO;
import io.beesports.mappers.StandingMapper;
import io.beesports.repositories.IStandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StandingService {

    private final IStandingRepository standingRepository;
    private final StandingMapper standingMapper;

    @Autowired
    public StandingService(IStandingRepository standingRepository, StandingMapper standingMapper) {
        this.standingRepository = standingRepository;
        this.standingMapper = standingMapper;
    }

    public List<StandingResponseDTO> getStandingsByTournament(String tournamentId) {
        List<Integer> tournamentIds = Arrays.stream(tournamentId.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        return standingRepository.findAllByTournamentIdInOrderByRank(tournamentIds).stream()
                .map(standingMapper::entityToDto)
                .collect(Collectors.toList());
    }

}
