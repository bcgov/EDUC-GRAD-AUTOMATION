package ca.bc.gov.educ.gtts.io;

import ca.bc.gov.educ.gtts.exception.IOUtilsException;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IOUtils {

    /**
     * Thwart instantiation
     */
    private IOUtils(){}

    public static void logOutputStreamToFile(String location, String fileName) throws IOUtilsException {
        if(!location.endsWith("\\")){
            location = location + "\\";
        }
        location = location + fileName + "_" + getDateTime() + ".log";
        try {
            Path path = Paths.get(location);
            File outputFile = path.toFile();
            outputFile.createNewFile();
            TeeOutputStream splitStream;
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                splitStream = new TeeOutputStream(System.out, fos);
            }
            PrintStream ps = new PrintStream(splitStream);
            System.setOut(ps);
            System.setErr(ps);
        } catch (IOException e) {
            throw new IOUtilsException(e);
        }
    }

    private static String getDateTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        return myDateObj.format(myFormatObj);
    }

}
