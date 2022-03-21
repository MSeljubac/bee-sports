package io.beesports.mappers;

import io.beesports.domain.dtos.ScoreDTO;
import io.beesports.domain.entities.Score;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScoreMapper {

    public ScoreDTO entityToDto(Score score) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setScore(score.getScore());
        scoreDTO.setTeam_id(score.getTeamId());
        return scoreDTO;
    }

    public List<ScoreDTO> entityListToDtoList(List<Score> scores) {
        List<ScoreDTO> list = new ArrayList<>();

        scores.forEach(score -> {
            ScoreDTO scoreDTO = new ScoreDTO();
            scoreDTO.setScore(score.getScore());
            scoreDTO.setTeam_id(score.getTeamId());
            list.add(scoreDTO);
        });

        return list;
    }

}
