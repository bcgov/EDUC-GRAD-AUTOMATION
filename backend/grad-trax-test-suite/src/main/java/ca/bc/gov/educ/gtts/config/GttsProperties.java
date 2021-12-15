package ca.bc.gov.educ.gtts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriTemplate;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "gtts-properties")
public class GttsProperties {

    private String gradApiBaseUrl;
    private String osHostedUrl;
    private Map<String, String> serviceEndpoints;

    public GttsProperties() {}

    public GttsProperties(String gradApiBaseUrl, Map<String, String> serviceEndpoints) {
        this.gradApiBaseUrl = gradApiBaseUrl;
        this.serviceEndpoints = serviceEndpoints;
    }

    public String getGradApiBaseUrl() {
        return gradApiBaseUrl;
    }

    public void setGradApiBaseUrl(String gradApiBaseUrl) {
        this.gradApiBaseUrl = gradApiBaseUrl;
    }

    public String getOsHostedUrl() {
        return osHostedUrl;
    }

    public void setOsHostedUrl(String osHostedUrl) {
        this.osHostedUrl = osHostedUrl;
    }

    public Map<String, String> getServiceEndpoints() {
        return serviceEndpoints;
    }

    public void setServiceEndpoints(Map<String, String> serviceEndpoints) {
        this.serviceEndpoints = serviceEndpoints;
    }

    public String getEndPoint(String key) {
        return (serviceEndpoints.containsKey(key)) ? serviceEndpoints.get(key) : null;
    }

    public String getAndExpandEndPoint(String key, Map<String, String> uriVariables) {
        UriTemplate uriTemplate = new UriTemplate(this.getEndPoint(key));
        return uriTemplate.expand(uriVariables).toString();
    }
}
