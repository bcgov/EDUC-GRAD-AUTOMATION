package ca.bc.gov.gradtraxtestclient.services;

import ca.bc.gov.gradtraxtestclient.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxtestclient.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GenericHTTPRequestServiceImpl implements GenericHTTPRequestService {

    private OAuth2RestOperations webClient;

    @Autowired
    public GenericHTTPRequestServiceImpl(OAuth2RestOperations webClient) {
        this.webClient = webClient;
    }

    @Override
    public <T> T get(String url, Class<T> clazz) throws GenericHTTPRequestServiceException, NotFoundException {
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
            throw new GenericHTTPRequestServiceException(msg);
        }
        return body;
    }

    @Override
    public <T> String post(String url, Class<T> clazz, Object obj) throws GenericHTTPRequestServiceException {
        HttpEntity<?> entity = new HttpEntity<>(obj, new HttpHeaders());
        ResponseEntity<?> response = null;
        try {
            response = webClient.exchange(url, HttpMethod.POST, entity, clazz);
        } catch (RestClientException e) {
            throw new GenericHTTPRequestServiceException(e);
        }
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new GenericHTTPRequestServiceException("There was a problem POSTing to the api.");
        }
        // get location from response, get new index
        String location = response.getHeaders().getLocation().toString();
        return location;
    }

    @Override
    public <T> T put(String url, Class<T> clazz, Object obj) throws GenericHTTPRequestServiceException {
        HttpEntity<?> entity = new HttpEntity<>(obj, new HttpHeaders());
        ResponseEntity<?> response = null;
        try {
            response = webClient.exchange(url, HttpMethod.PUT, entity, clazz);
        } catch (RestClientException e) {
            throw new GenericHTTPRequestServiceException(e);
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GenericHTTPRequestServiceException("There was a problem PUTing to api.");
        }
        return (T) response.getBody();
    }

    @Override
    public String encodeParams(String url) {
        String encodedURL = url;
        try {
            url = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("WARN: " + e .getMessage());
        }
        return url;
    }
}
