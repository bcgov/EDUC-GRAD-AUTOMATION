package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;


/**
 * Generic HTTP helpers
 */
public interface GenericHTTPRequestService {

    <T> T get(String url, Class<T> clazz) throws GenericHTTPRequestServiceException, NotFoundException;

    <T> String post(String url, Class<T> clazz, Object obj) throws GenericHTTPRequestServiceException;

    <T> T put(String url, Class<T> clazz, Object obj) throws GenericHTTPRequestServiceException;

    String encodeParams(String url);

}
