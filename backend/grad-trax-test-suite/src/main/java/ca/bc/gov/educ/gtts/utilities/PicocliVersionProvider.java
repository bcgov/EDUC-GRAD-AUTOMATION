package ca.bc.gov.educ.gtts.utilities;

import picocli.CommandLine;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PicocliVersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        URL url = getClass().getResource("/version.txt");
        if (url == null) {
            return new String[] {"No version.txt file found in the classpath."};
        }
        Properties properties = new Properties();
        try (InputStream is = url.openStream()){
            properties.load(is);
        }
        return new String[] {
                "version: " + properties.getProperty("version") + " built: " + properties.getProperty("build.time")
        };
    }

}
