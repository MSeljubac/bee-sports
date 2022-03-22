package io.beesports.config;

import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SyncScheduler {

    @Value("${pandascore.scheduler}")
    private boolean schedulerOn;

    //@Scheduled(cron = "0 0/60 * 1/1 * ?")
    public void hourlyRefresh() throws IOException, FeedException {
        if (schedulerOn) {
            // TODO: Add matches to sync at an hourly rate.
        }
    }

}
