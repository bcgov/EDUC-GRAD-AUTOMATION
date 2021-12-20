package ca.bc.gov.educ.gtts.config;

import ca.bc.gov.educ.gtts.exception.IOUtilsException;
import ca.bc.gov.educ.gtts.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.ArrayList;
import java.util.List;

@EnableOAuth2Client
@Configuration
public class Config {

    private GttsProperties gttsProperties;

    @Autowired
    public Config(GttsProperties gttsProperties) {
        this.gttsProperties = gttsProperties;
    }

    @Bean
    public ModelMapper getModelMapper(){return new ModelMapper();}

    @Bean
    public RestTemplate getWebClient(){
        return new RestTemplate();
    }


    @Bean
    protected OAuth2ProtectedResourceDetails resource() {
        ResourceOwnerPasswordResourceDetails resource;
        resource = new ResourceOwnerPasswordResourceDetails();

        resource.setAccessTokenUri(gttsProperties.getEndPoint("keycloak-auth-host") + "/auth/realms/master/protocol/openid-connect/token");
        resource.setClientId(gttsProperties.getAuthValue("student-api-client-id"));
        resource.setClientSecret(gttsProperties.getAuthValue("student-api-client-secret"));
        resource.setGrantType("password");
        resource.setUsername(gttsProperties.getAuthValue("student-api-username"));
        resource.setPassword(gttsProperties.getAuthValue("student-api-password"));
        return resource;
    }

    @Bean
    public OAuth2RestOperations restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
    }

    /**
     * Default logging is set to stdout. If a logFileLocation is present in the
     * build config then the logging stream is split to both file and stdout.
     * @param logFileLocation a valid location for the log file
     * @throws IOUtilsException
     */
    public static void setUpLogging(String logFileLocation) throws IOUtilsException {
        if(logFileLocation != null){
            IOUtils.logOutputStreamToFile(logFileLocation, "test");
        }
    }

}
