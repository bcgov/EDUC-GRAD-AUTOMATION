package ca.bc.gov.educ.gtts.services.trax;

import ca.bc.gov.educ.gtts.config.GttsProperties;
import ca.bc.gov.educ.gtts.exception.TraxBatchServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TraxBatchServiceImpl implements TraxBatchService {

    private GttsProperties gttsProperties;
    private RestTemplate webClient;

    @Autowired
    public TraxBatchServiceImpl(GttsProperties gttsProperties, RestTemplate webClient) {
        this.gttsProperties = gttsProperties;
        this.webClient = webClient;
    }

    @Override
    public boolean runTest() {
        System.out.println(gttsProperties.getGradApiBaseUrl());
        return true;
    }

    /**
     * Convenience method for get requests
     * @param url the url of the GET service end-point and the class of the expected response entity.
     * @return
     * @throws TraxBatchServiceException
     * @throws NotFoundException
     */
    private <T> T get(String url, Class<T> clazz) throws TraxBatchServiceException, NotFoundException {
        ResponseEntity<?> response = null;
        T body = null;
        try {
            response = webClient.getForEntity(url, clazz);
            body = (T) response.getBody();
        } catch (RestClientException e) {
            String msg = e.getMessage();
            if(e instanceof HttpClientErrorException){
                if(((HttpClientErrorException) e).getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new NotFoundException();
                }
            }
            throw new TraxBatchServiceException(msg);
        }
        return body;
    }

    /**
     * Generics based POST
     * @param url the url to post to
     * @param clazz the class type of the entity
     * @param obj the entity itself
     * @param <T>
     * @return the location of the created resource
     * @throws TraxBatchServiceException
     */
    private <T> String post(String url, Class<T> clazz, Object obj) throws TraxBatchServiceException {
        HttpEntity<?> entity = new HttpEntity<>(obj, new HttpHeaders());
        ResponseEntity<?> response = null;
        try {
            response = webClient.exchange(url, HttpMethod.POST, entity, clazz);
        } catch (RestClientException e) {
            throw new TraxBatchServiceException(e);
        }
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new TraxBatchServiceException(
                    "There was a problem POSTing to api.");
        }
        // get location from response, get new index
        String location = response.getHeaders().getLocation().toString();
        return location;
    }

    private <T> T put(String url, Class<T> clazz, Object obj) throws TraxBatchServiceException {
        HttpEntity<?> entity = new HttpEntity<>(obj, new HttpHeaders());
        ResponseEntity<?> response = null;
        try {
            response = webClient.exchange(url, HttpMethod.PUT, entity, clazz);
        } catch (RestClientException e) {
            throw new TraxBatchServiceException(e);
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new TraxBatchServiceException(
                    "There was a problem PUTing to api.");
        }
        return (T) response.getBody();
    }

    /**
     * Encodes a url or returns original if encoding exception occurs
     * @param url
     * @return
     */
    private static String encodeParams(String url) {
        String encodedURL = url;
        try {
            url = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("WARN: " + e .getMessage());
        }
        return url;
    }

}
