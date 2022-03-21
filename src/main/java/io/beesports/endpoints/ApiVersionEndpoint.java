package io.beesports.endpoints;

import io.beesports.config.ConfigConsts;
import io.beesports.domain.dtos.commands.LinkChangelogsToApiVersionCommand;
import io.beesports.domain.entities.ApiVersion;
import io.beesports.services.ApiVersionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@RestController
//@RequestMapping("/version")
public class ApiVersionEndpoint {

    private final ApiVersionService apiVersionService;

    @Autowired
    public ApiVersionEndpoint(ApiVersionService apiVersionService) {
        this.apiVersionService = apiVersionService;
    }

    @Operation(summary = "Gets all ApiVersions.")
    @GetMapping(value = "/all", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public List<ApiVersion> getAllApiVersions(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion) {
        return apiVersionService.getAllApiVersions();
    }

    @Operation(summary = "Adds a new ApiVersion.")
    @PostMapping(value = "/add", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public ApiVersion addApiVersion(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                    @RequestParam("version") String version) {
        return apiVersionService.addApiVersion(version);
    }

    @Operation(summary = "Links Changelog entry to an ApiVersion.")
    @PostMapping(value = "/link", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public ApiVersion addChangelogsToApiVersion(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                                @RequestBody LinkChangelogsToApiVersionCommand command) {
        return apiVersionService.addChangelogsToApiVersion(command);
    }

    @Operation(summary = "Deletes an ApiVersion.")
    @DeleteMapping(value = "/delete", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public String deleteApiVersion(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion,
                                   @RequestParam("id") UUID id) {
        return apiVersionService.deleteApiVersion(id);
    }

}

