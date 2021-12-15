package ca.bc.gov.educ.gtts.commands;

import ca.bc.gov.educ.gtts.utilities.PicocliVersionProvider;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * The root of the command tree.
 */
@CommandLine.Command(
        name = "gradt",
        description = "A command line client test utility for GRAD.",
        versionProvider = PicocliVersionProvider.class,
        mixinStandardHelpOptions = true,
        synopsisSubcommandLabel = "COMMAND",
        subcommands = {IntegrationCommand.class}
)
@Component
public class BaseCommand implements Callable<Integer> {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }
}
