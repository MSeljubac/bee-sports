package io.beesports.services;

import io.beesports.domain.dtos.responses.AnnouncementResponseDTO;
import io.beesports.mappers.AnnouncementMapper;
import io.beesports.repositories.IAnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final IAnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;

    @Autowired
    public AnnouncementService(IAnnouncementRepository announcementRepository, AnnouncementMapper announcementMapper) {
        this.announcementRepository = announcementRepository;
        this.announcementMapper = announcementMapper;
    }

    public List<AnnouncementResponseDTO> getAnnouncements() {
        return announcementRepository.getAllNonExpiredAnnouncements(new Date()).stream()
                .map(announcementMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

}
