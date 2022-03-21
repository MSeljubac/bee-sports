package io.beesports.mappers;

import io.beesports.domain.dtos.responses.SerieResponseDTO;
import io.beesports.domain.entities.Serie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SerieMapper {

    public List<SerieResponseDTO> entityToResponseDto(List<Serie> series){
        List<SerieResponseDTO> list = new ArrayList<>();

        series.forEach(serie -> {
            SerieResponseDTO serieResponseDTO = new SerieResponseDTO();
            serieResponseDTO.setId(serie.getId());
            serieResponseDTO.setFullName(serie.getFullName());
            serieResponseDTO.setBeginAt(serie.getBeginAt());
            serieResponseDTO.setEndAt(serie.getEndAt());
            serieResponseDTO.setDescription(serie.getDescription());
            serieResponseDTO.setWinnerId(serie.getWinnerId());
            list.add(serieResponseDTO);
        });

        return list;
    }
}
