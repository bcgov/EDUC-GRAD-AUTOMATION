package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;


/**
 * Generic HTTP helpers
 */
public interface GenericHTTPRequestService {

    <T> T get(String url, Class<T> clazz) throws GenericHTTPRequestServiceException, NotFoundException;

    <T> String post(String url, Class<T> clazz, Object obj) throws GenericHTTPRequestServiceException;

    <T> T put(String url, Class<T> clazz, Object obj) throws GenericHTTPRequestServiceException;

    String encodeParams(String url);

}
