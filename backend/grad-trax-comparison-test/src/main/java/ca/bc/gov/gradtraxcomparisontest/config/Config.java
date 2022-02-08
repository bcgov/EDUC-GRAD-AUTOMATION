package ca.bc.gov.gradtraxcomparisontest.config;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@EnableOAuth2Client
@Configuration
public class Config {

    AppProperties appProperties;

    @Autowired
    public Config(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public WebClient getReactiveWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    public ModelMapper getModelMapper(){return new ModelMapper();}

    @Bean
    public RestTemplate getWebClient(){
        return new RestTemplate();
    }

    @Bean
    public Javers getJavers(){
        return JaversBuilder.javers().build();
    }


    @Bean
    protected OAuth2ProtectedResourceDetails resource() {
        ResourceOwnerPasswordResourceDetails resource;
        resource = new ResourceOwnerPasswordResourceDetails();

        resource.setAccessTokenUri(appProperties.getEndPoint("keycloak-auth-host") + "/auth/realms/master/protocol/openid-connect/token");
        resource.setClientId(appProperties.getAuthValue("student-api-client-id"));
        resource.setClientSecret(appProperties.getAuthValue("student-api-client-secret"));
        resource.setGrantType("password");
        resource.setUsername(appProperties.getAuthValue("student-api-username"));
        resource.setPassword(appProperties.getAuthValue("student-api-password"));
        return resource;
    }

    @Bean
    public OAuth2RestOperations restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
    }

}
