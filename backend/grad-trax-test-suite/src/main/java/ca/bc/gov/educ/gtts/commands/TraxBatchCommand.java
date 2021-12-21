package ca.bc.gov.educ.gtts.commands;

import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;
import ca.bc.gov.educ.gtts.model.utils.TestPens;
import ca.bc.gov.educ.gtts.services.TraxBatchService;
import ca.bc.gov.educ.gtts.services.TraxService;
import ca.bc.gov.educ.gtts.utilities.JSONUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command (
        name = "traxbatch",
        description = "Runs a batch test against trax data.",
        mixinStandardHelpOptions = true
)
@Component
public class TraxBatchCommand implements Callable<Integer> {

    private TraxBatchService traxBatchService;
    private TraxService traxService;

    @CommandLine.Option(
            names = {"-f", "--file"},
            description = "A path to the file containing test pens.",
            required = true
    )
    String filePath;

    @Autowired
    public TraxBatchCommand(TraxBatchService traxBatchService, TraxService traxService) {
        this.traxBatchService = traxBatchService;
        this.traxService = traxService;
    }

    @Override
    public Integer call() throws Exception {
        // TODO: update methods
        TestPens testPens = JSONUtilities.serializeJSONFileToObject(filePath, TestPens.class);
        traxBatchService.runTest(testPens.getTestPens());
        return 0;
    }

}
