package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.entities.Changelog;
import io.beesports.services.ChangelogService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@RestController
//@RequestMapping("/changelog")
public class ChangelogEndpoint {

    private final ChangelogService changelogService;

    @Autowired
    public ChangelogEndpoint(ChangelogService changelogService) {
        this.changelogService = changelogService;
    }

    @Operation(summary = "Gets all Changelog entries.")
    @GetMapping(value = "/all", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<Changelog> getAllChangelogs(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion) {
        return changelogService.getAllChangelogs();
    }

    @Operation(summary = "Creates changelog entries in bulk.")
    @PostMapping(value = "/bulk", headers = "Accept-version"+ ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<Changelog> createChangelogs(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                            @RequestBody List<Changelog> changelogs) {
        return changelogService.createChangelogsBulk(changelogs);
    }

    @Operation(summary = "Deletes a changelog entry.")
    @DeleteMapping(value = "/remove", headers = "Accept-version="+ ConfigConsts.LATEST_ACCEPT_VERSION)
    public String deleteChangelog(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                  @RequestParam("id") UUID changelogId) {
        return changelogService.deleteChangelog(changelogId);
    }

}
