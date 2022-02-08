package ca.bc.gov.gradtraxtestclient.config;

import ca.bc.gov.gradtraxtestclient.model.config.Program;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriTemplate;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "gtts-properties")
public class GttsProperties {

    private String gradApiBaseUrl;
    private String osHostedUrl;
    private String educGradStudentApi;
    private String educGradAlgorithmApi;
    private Map<String, String> serviceEndpoints;
    private Map<String, String> auth;
    private List<Program> requirementMappings;

    public GttsProperties() {}

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

    public Map<String, String> getAuth() {
        return auth;
    }

    public void setAuth(Map<String, String> auth) {
        this.auth = auth;
    }

    public String getAuthValue(String key) {
        return (auth.containsKey(key)) ? auth.get(key) : null;
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

    public List<Program> getRequirementMappings() {
        return requirementMappings;
    }

    public void setRequirementMappings(List<Program> requirementMappings) {
        this.requirementMappings = requirementMappings;
    }

    public String getEducGradStudentApi() {
        return educGradStudentApi;
    }

    public void setEducGradStudentApi(String educGradStudentApi) {
        this.educGradStudentApi = educGradStudentApi;
    }

    public String getEducGradAlgorithmApi() {
        return educGradAlgorithmApi;
    }

    public void setEducGradAlgorithmApi(String educGradAlgorithmApi) {
        this.educGradAlgorithmApi = educGradAlgorithmApi;
    }
}
