package io.beesports.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {

    @Bean
    public OkHttpClient OkHttpClient() {
        return new OkHttpClient();
    }

}
