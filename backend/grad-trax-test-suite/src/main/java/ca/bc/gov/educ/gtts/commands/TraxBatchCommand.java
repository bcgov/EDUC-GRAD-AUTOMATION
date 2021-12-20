package ca.bc.gov.educ.gtts.commands;

import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;
import ca.bc.gov.educ.gtts.services.TraxBatchService;
import ca.bc.gov.educ.gtts.services.TraxService;
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

    /*@CommandLine.Option(
            names = {"-id", "--index-id"},
            description = "The unique id of the index."
    )
    String indexId;*/

    @Autowired
    public TraxBatchCommand(TraxBatchService traxBatchService, TraxService traxService) {
        this.traxBatchService = traxBatchService;
        this.traxService = traxService;
    }

    @Override
    public Integer call() throws Exception {
        // TODO: update methods
        //traxBatchService.runTest();
        TraxStudentDto traxStudentDto = traxService.findTraxStudentByPEN("135290237");
        System.out.println(traxStudentDto.getAddress1());
        return 0;
    }

}
