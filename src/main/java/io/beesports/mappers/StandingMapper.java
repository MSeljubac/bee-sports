package io.beesports.mappers;

import io.beesports.domain.dtos.StandingDTO;
import io.beesports.domain.dtos.responses.StandingResponseDTO;
import io.beesports.domain.entities.Standing;
import io.beesports.domain.entities.Tournament;
import io.beesports.repositories.ILeagueRepository;
import io.beesports.repositories.ISeriesRepository;
import io.beesports.repositories.IStandingRepository;
import io.beesports.repositories.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class StandingMapper {

    private final IStandingRepository standingRepository;
    private final ISeriesRepository seriesRepository;
    private final ILeagueRepository leagueRepository;
    private final ITeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final TournamentMapper tournamentMapper;

    @Autowired
    public StandingMapper(IStandingRepository standingRepository, ISeriesRepository seriesRepository,
                          ILeagueRepository leagueRepository, ITeamRepository teamRepository,
                          TeamMapper teamMapper, TournamentMapper tournamentMapper) {
        this.standingRepository = standingRepository;
        this.seriesRepository = seriesRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.tournamentMapper = tournamentMapper;
    }

    public StandingResponseDTO entityToDto(Standing standing){
        StandingResponseDTO standingResponseDTO = new StandingResponseDTO();

        standingResponseDTO.setId(standing.getId());
        standingResponseDTO.setWins(standing.getWins());
        standingResponseDTO.setLosses(standing.getLosses());
        standingResponseDTO.setTotal(standing.getTotal());
        standingResponseDTO.setRank(standing.getRank());
        standingResponseDTO.setTournamentId(standing.getTournament().getId());
        standingResponseDTO.setTournamentName(standing.getTournament().getName());
        standingResponseDTO.setSerieName(standing.getTournament().getSerie().getFullName());
        standingResponseDTO.setLeagueName(standing.getTournament().getLeague().getName());
        standingResponseDTO.setTeam(teamMapper.entityToDtoList(Collections.singletonList(standing.getTeam())).get(0));

        return standingResponseDTO;
    }

    public Standing dtoToEntity(StandingDTO standingDTO, Tournament tournament){
        Standing standing = standingRepository.findByTeamIdAndTournamentId(standingDTO.getTeam().getId(), tournament.getId());

        if(standing == null){
            standing = new Standing();
            standing.setId(UUID.randomUUID());
        }

        standing.setTeam(teamRepository.getOne(standingDTO.getTeam().getId()));
        standing.setTournament(tournament);
        standing.setLosses(standingDTO.getLosses());
        standing.setWins(standingDTO.getWins());
        standing.setTotal(standingDTO.getTotal());
        standing.setRank(standingDTO.getRank());

        return standing;
    }

}
