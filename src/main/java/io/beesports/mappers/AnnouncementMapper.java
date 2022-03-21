package io.beesports.mappers;

import io.beesports.domain.dtos.responses.AnnouncementResponseDTO;
import io.beesports.domain.entities.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {

    public AnnouncementResponseDTO entityToResponseDto(Announcement announcement) {
        AnnouncementResponseDTO responseDTO = new AnnouncementResponseDTO();
        responseDTO.setId(announcement.getId());
        responseDTO.setContent(announcement.getContent());
        responseDTO.setExpiryDate(announcement.getExpiryDate());
        responseDTO.setType(announcement.getType());
        return responseDTO;
    }

}
