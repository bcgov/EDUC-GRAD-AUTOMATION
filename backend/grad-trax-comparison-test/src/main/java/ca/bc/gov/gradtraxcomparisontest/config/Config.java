package ca.bc.gov.gradtraxcomparisontest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.reactive.function.client.WebClient;

@EnableOAuth2Client
@Configuration
public class Config {

    @Bean
    public WebClient getReactiveWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    public ModelMapper getModelMapper(){return new ModelMapper();}

}
