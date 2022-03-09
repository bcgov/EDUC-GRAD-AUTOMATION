package ca.bc.gov.educ.gmts.utils;

import ca.bc.gov.educ.gmts.config.RequiredProperties;
import ca.bc.gov.educ.gmts.config.TestConfig;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class URLUtils {

    public static String getURL(RequiredProperties requiredProperties, String path, Map<String, String> params) throws URISyntaxException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(
                new URI(TestConfig.getInstance().getApiEndPoint(requiredProperties)));
        if(path != null && path.length() > 0){
            builder.path(path);
        }
        if(params != null && !params.isEmpty()){
            builder.queryParams(convertMaptToMultiValueMap(params));
        }
        return builder.build().toString();
    }

    private static MultiValueMap<String, String> convertMaptToMultiValueMap(Map<String, String> params){
        MultiValueMap<String, String> p = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            p.add(entry.getKey(), entry.getValue());
        }
        return p;
    }


}
