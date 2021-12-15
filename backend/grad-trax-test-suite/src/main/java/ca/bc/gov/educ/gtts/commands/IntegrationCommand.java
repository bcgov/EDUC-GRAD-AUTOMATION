package ca.bc.gov.educ.gtts.commands;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;


/**
 * @author CDITCHER
 * Top level command class for handling 'integration' tests.
 */
@CommandLine.Command(
        name = "integration",
        description = "Perform integration tests.",
        mixinStandardHelpOptions = true,
        subcommands = {TraxBatchCommand.class}
)
@Component
public class IntegrationCommand implements Callable<Integer> {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        // There is no direct call to this command, so this will show error, help, etc.
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }

}
