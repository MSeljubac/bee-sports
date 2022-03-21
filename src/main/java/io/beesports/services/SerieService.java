package io.beesports.services;

import io.beesports.domain.dtos.responses.SerieResponseDTO;
import io.beesports.mappers.SerieMapper;
import io.beesports.repositories.ISeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final ISeriesRepository seriesRepository;
    private final SerieMapper serieMapper;

    @Autowired
    public SerieService(ISeriesRepository seriesRepository, SerieMapper serieMapper) {
        this.seriesRepository = seriesRepository;
        this.serieMapper = serieMapper;
    }

    public List<SerieResponseDTO> getSeriesByLeague(Integer leagueId){
        return serieMapper.entityToResponseDto(seriesRepository.findAllByLeagueId(leagueId));
    }

}
