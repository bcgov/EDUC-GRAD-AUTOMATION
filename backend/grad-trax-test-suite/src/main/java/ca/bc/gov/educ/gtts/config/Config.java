package ca.bc.gov.educ.gtts.config;

import ca.bc.gov.educ.gtts.exception.IOUtilsException;
import ca.bc.gov.educ.gtts.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate getWebClient(){
        return new RestTemplate();
    }

    /**
     * Default logging is set to stdout. If a logFileLocation is present in the
     * build config then the logging stream is split to both file and stdout.
     * @param logFileLocation a valid location for the log file
     * @throws IOUtilsException
     */
    public static void setUpLogging(String logFileLocation) throws IOUtilsException {
        //String logFileLocation = buildConfig.getEnvironmentString("logFileLocation");
        if(logFileLocation != null){
            IOUtils.logOutputStreamToFile(logFileLocation, "test");
        }
    }

}
