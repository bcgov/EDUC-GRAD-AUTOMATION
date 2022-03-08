package ca.bc.gov.educ.gmts.config;

import com.google.common.collect.ImmutableList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class TestConfig {

    private static TestConfig instance;
    private Properties properties;

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
    private void init() {
        // load properties
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(".properties")) {
            this.properties = new Properties();
            this.properties.load(is);
            for(RequiredProperties prop : RequiredProperties.values()){
                if(this.properties.getProperty(prop.name()) == null){
                    throw new RuntimeException("All properties must be set! Missing property: " + prop.name() + ". See RequiredProperties enum.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file. Has one been set?");
        }
    }

    /**
     * Sets up a OAuth2RestOperations template for OAUTH2 http(s) request to apis
     * @return
     */
    public OAuth2RestOperations getOauth2RestOperations() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(getOauth2ProtectedResourceDetails(), new DefaultOAuth2ClientContext(atr));
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
            }
        }
        return restTemplate;
    }

    /**
     * Sets authentication details
     * @return
     */
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

    /**
     * Convenience method for value for a property from a RequiredProperties enum
     * @param prop Example: RequiredProperties.STUDENT_API_USERNAME
     * @return the value set
     */
    private String getProperty(RequiredProperties prop){
        return this.properties.getProperty(prop.name());
    }

    public String getApiEndPoint(RequiredProperties prop) {
        return getProperty(prop) + "-" + getProperty(RequiredProperties.OS_HOSTED_URL);
    }

}
