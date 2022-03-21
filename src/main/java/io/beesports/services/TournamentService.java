package io.beesports.services;

import io.beesports.domain.dtos.responses.TournamentResponseDTO;
import io.beesports.domain.entities.Serie;
import io.beesports.mappers.TournamentMapper;
import io.beesports.repositories.ISeriesRepository;
import io.beesports.repositories.ITournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TournamentService {

    private final ITournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;
    private final ISeriesRepository seriesRepository;

    @Autowired
    public TournamentService(ITournamentRepository tournamentRepository, TournamentMapper tournamentMapper,
                             ISeriesRepository seriesRepository) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
        this.seriesRepository = seriesRepository;
    }

    public List<TournamentResponseDTO> getTournamentsBySerieId(Integer serieId) {
        List<TournamentResponseDTO> resultList = new ArrayList<>();

        tournamentRepository.findAllBySerieId(serieId).forEach(tournament -> {
            resultList.add(tournamentMapper.entityToResponseDto(tournament));
        });

        return resultList;
    }

    public List<TournamentResponseDTO> getTournamentsByLeagueId(Integer leagueId) {
        List<TournamentResponseDTO> resultList = new ArrayList<>();

        tournamentRepository.fetchAllFromLeagueId(leagueId).forEach(tournament -> {
            resultList.add(tournamentMapper.entityToResponseDto(tournament));
        });

        return resultList;
    }

    public List<TournamentResponseDTO> getAllActiveTournaments() {
        List<TournamentResponseDTO> responseDTOList = new ArrayList<>();

        List<Serie> activeSeries = seriesRepository.getLatestSeries();
        activeSeries.forEach(serie -> {
            serie.getTournaments().forEach(tournament -> {
                if (tournament.getEndAt().after(new Date())) {
                    responseDTOList.add(tournamentMapper.entityToResponseDto(tournament));
                }
            });
        });

        return responseDTOList;
    }

}
