package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.responses.AnnouncementResponseDTO;
import io.beesports.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementEndpoint {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementEndpoint(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping(value = "/", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<AnnouncementResponseDTO> getAnnouncements(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion) {
        return announcementService.getAnnouncements();
    }

}
