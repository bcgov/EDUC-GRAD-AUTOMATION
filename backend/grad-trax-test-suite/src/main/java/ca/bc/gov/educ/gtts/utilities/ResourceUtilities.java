package ca.bc.gov.educ.gtts.utilities;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Date;

/**
 * Utilities for working with resources in the application
 */
public class ResourceUtilities {

    private ResourceUtilities(){}

    /**
     * Attempts to get a resource as a String. This method is useful
     * for example for getting text based files that are on the classpath
     * (in resources folder for example)
     * You can call this method as such: getResourceAsString("classpath:myfile.json");
     * @param source
     * @return
     */
    public static String getResourceAsString(String source){
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(source);
        return asString(resource);
    }

    /**
     * Given an existing Resource object, this method will attempt to
     * convert to a String.
     * @param resource
     * @return
     */
    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Given a string will append a '_{uniqueid}' to the end.
     * Unique id is 8 characters long. For example, calling this function
     * as addUniqueIdToString("myString") might result in "myString_12jdT5hT"
     * @param str
     * @return
     */
    public static String addUniqueIdToString(String str) {
        String rand = Long.toString(new Date().getTime());
        rand = rand.substring(8, rand.length());
        return str + "_" + rand;
    }

}
