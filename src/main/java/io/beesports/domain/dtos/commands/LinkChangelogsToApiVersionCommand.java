package io.beesports.domain.dtos.commands;

import io.beesports.domain.entities.Changelog;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LinkChangelogsToApiVersionCommand {

    private List<UUID> changelogIds;
    private UUID apiVersionId;

}
