package ca.bc.gov.educ.gtts.commands;

import ca.bc.gov.educ.gtts.services.TraxBatchService;
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

    /*@CommandLine.Option(
            names = {"-id", "--index-id"},
            description = "The unique id of the index."
    )
    String indexId;*/

    @Autowired
    public TraxBatchCommand(TraxBatchService traxBatchService) {
        this.traxBatchService = traxBatchService;
    }

    @Override
    public Integer call() throws Exception {
        // TODO: update methods
        traxBatchService.runTest();
        return 0;
    }

}
