package ca.bc.gov.gradtraxtestclient.utilities;

import ca.bc.gov.gradtraxtestclient.exception.JSONUtilitiesException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.List;

public class JSONUtilities {

    /**
     * private constructor
     */
    private JSONUtilities(){}

    /**
     * Pretty prints valid json. This method follow the GIGO principal (Garbage In, Garbage Out)
     * If you attempt to pas crappy, mal-formed json to this method, it will not produce an error
     * but will just return your garbage ;)
     * @param json valid json
     * @return pretty printed valid json
     */
    public static String prettyPrint(String json){
        String prettyJson = json;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObj = mapper.readValue(json, Object.class);
            prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (JsonProcessingException e) {
                // ignore, we're just returning the input if anything effs up.
            }
        return prettyJson;
    }

    /**
     * Given the location of a json object and a class type, this method
     * will attempt to serialize to a java object of that type.
     * @param pathToFile
     * @param clazz
     * @param <T>
     * @return
     * @throws JSONUtilitiesException
     */
    public static <T> T serializeJSONFileToObject(String pathToFile, Class<T> clazz) throws JSONUtilitiesException {
        try {
            URL file = new URL("file:" + pathToFile);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, clazz);
        } catch (Exception e) {
            throw new JSONUtilitiesException(e.getMessage());
        }
    }

    /**
     * A convenience method for serializing JSON to an object type.
     * Example: CivixDoc doc = serializeJSONToObject(docString, CivixDoc.class) should return
     * a CivixDoc object if docString describes it as valid JSON or null if not.
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T serializeJSONToObject(String json, Class<T> clazz) throws JSONUtilitiesException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JSONUtilitiesException(e.getMessage());
        }
    }

    /**
     * Convenience method for converting lists of objects to other types
     * @param list
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T serializeListOfJSONToObjectList(List<T> list, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(list, typeReference);
    }

    /**
     * Attempts to de-serialize an object to JSON.
     * @return
     */
    public static String deSerializeObjectToJSON(Object object) throws JSONUtilitiesException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONUtilitiesException(e.getMessage());
        }
    }

}
