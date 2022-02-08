package ca.bc.gov.educ.gtts.commands;

import ca.bc.gov.educ.gtts.config.GttsProperties;
import ca.bc.gov.educ.gtts.services.TraxBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import static ca.bc.gov.educ.gtts.filters.ListFilters.filterByProgram;

import java.util.concurrent.Callable;

@CommandLine.Command (
        name = "traxbatch",
        description = "Runs a batch test against trax data.",
        mixinStandardHelpOptions = true
)
@Component
public class TraxBatchCommand implements Callable<Integer> {

    private TraxBatchService traxBatchService;
    private GttsProperties properties;

    @CommandLine.Option(
            names = {"-f", "--file"},
            description = "A path to the file containing test pens.",
            required = false
    )
    String filePath;
    @CommandLine.Option(
            names = {"-PF", "--programFilter"},
            description = "Filter by program, examples include: 1950, 2004-EN, etc.",
            required = false
    )
    String programFilter;

    @Autowired
    public TraxBatchCommand(TraxBatchService traxBatchService, GttsProperties gttsProperties) {
        this.traxBatchService = traxBatchService;
        this.properties = gttsProperties;
    }

    @Override
    public Integer call() throws Exception {
        // TODO: update methods
        //TestPens testPens = JSONUtilities.serializeJSONFileToObject(filePath, TestPens.class);
        //traxBatchService.runTest(testPens.getTestPens());
        //System.out.println("pause");
        if ((programFilter != null)) {
            traxBatchService.runTest(filterByProgram(programFilter));
        } else {
            traxBatchService.runTest();
        }
        return 0;
    }

}
