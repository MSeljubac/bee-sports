package io.beesports.endpoints;

import io.beesports.bootstrap.DataInitializer;
import io.beesports.config.ConfigConsts;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class SyncEndpoint {

    private final DataInitializer dataInitializer;

    @Autowired
    public SyncEndpoint(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    @Operation(summary = "Triggers full data sync process.")
    @PostMapping(value = "/trigger", headers = "Accept-version=" + ConfigConsts.LATEST_ACCEPT_VERSION)
    public void getTournamentsByLeague(@RequestHeader(value = "Accept-version", defaultValue = ConfigConsts.DEFAULT_ACCEPT_VERSION, required = false) String acceptVersion) throws Exception {
        dataInitializer.run();
    }

}
