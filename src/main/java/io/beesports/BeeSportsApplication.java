package io.beesports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeeSportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeeSportsApplication.class, args);
    }

}
