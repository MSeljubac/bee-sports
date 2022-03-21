package io.beesports.mappers;

import io.beesports.domain.dtos.responses.TournamentResponseDTO;
import io.beesports.domain.entities.Tournament;
import org.springframework.stereotype.Component;

@Component
public class TournamentMapper {

    public TournamentResponseDTO entityToResponseDto(Tournament tournament) {

        TournamentResponseDTO tournamentResponseDTO = new TournamentResponseDTO();

        tournamentResponseDTO.setId(tournament.getId());
        tournamentResponseDTO.setName(tournament.getName());
        tournamentResponseDTO.setBeginAt(tournament.getBeginAt());
        tournamentResponseDTO.setEndAt(tournament.getEndAt());
        tournamentResponseDTO.setPrizepool(tournament.getPrizepool());
        tournamentResponseDTO.setWinnerId(tournament.getWinnerId());
        tournamentResponseDTO.setSerieId(tournament.getSerie().getId());
        tournamentResponseDTO.setSerieName(tournament.getSerie().getFullName());
        tournamentResponseDTO.setLeagueId(tournament.getLeague().getId());
        tournamentResponseDTO.setLeagueName(tournament.getLeague().getName());
        tournamentResponseDTO.setLeagueImageUrl(tournament.getLeague().getImageUrl());

        return tournamentResponseDTO;

    }

}
