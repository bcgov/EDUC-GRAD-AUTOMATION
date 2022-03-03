package ca.bc.gov.educ.gmts.config;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.Properties;

public class TestConfig {

    private Properties properties;
    private static TestConfig instance;

    public static synchronized TestConfig getInstance(){

        if(instance == null){
            instance = new TestConfig();
        }
        return instance;
    }

    private TestConfig(){
        init();
    }

    /**
     * Initiate, test for system properties, etc.
     */
    private static void init() {
        Properties properties = System.getProperties();
        for(RequiredProperties prop : RequiredProperties.values()){
            if(!properties.containsKey(prop.name())){
                throw new RuntimeException("All properties must be set! Missing property: " + prop.name() + ". See RequiredProperties enum.");
            }
        }
    }

    public OAuth2RestOperations getOauth2RestOperations() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(getOauth2ProtectedResourceDetails(), new DefaultOAuth2ClientContext(atr));
    }

    protected OAuth2ProtectedResourceDetails getOauth2ProtectedResourceDetails() {
        ResourceOwnerPasswordResourceDetails resource;
        resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(getProperty(RequiredProperties.KEYCLOAK_AUTH_HOST) + "/auth/realms/master/protocol/openid-connect/token");
        resource.setClientId(getProperty(RequiredProperties.STUDENT_API_CLIENT_ID));
        resource.setClientSecret(getProperty(RequiredProperties.STUDENT_API_CLIENT_SECRET));
        resource.setGrantType("password");
        resource.setUsername(getProperty(RequiredProperties.STUDENT_API_USERNAME));
        resource.setPassword(getProperty(RequiredProperties.STUDENT_API_PASSWORD));
        return resource;
    }

    private String getProperty(RequiredProperties prop){
        return properties.getProperty(prop.name());
    }

}
