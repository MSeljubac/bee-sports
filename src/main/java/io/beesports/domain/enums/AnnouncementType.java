package io.beesports.domain.enums;

import lombok.Getter;

@Getter
public enum AnnouncementType {

    INFO("Info"),
    WARNING("Warning"),
    ERROR("Error");

    private String name;

    AnnouncementType(String name) {
        this.name = name;
    }

}
