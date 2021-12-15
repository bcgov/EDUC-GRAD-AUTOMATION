package ca.bc.gov.educ.gtts;

import ca.bc.gov.educ.gtts.commands.BaseCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class GttsApplication implements CommandLineRunner, ExitCodeGenerator {

	private CommandLine.IFactory iFactory;
	private BaseCommand baseCommand;
	private int exitCode;

	public GttsApplication(CommandLine.IFactory iFactory, BaseCommand baseCommand) {
		this.iFactory = iFactory;
		this.baseCommand = baseCommand;
	}

	public static void main(String[] args) {
		System.exit(
				SpringApplication.exit(
						SpringApplication.run(GttsApplication.class, args)
				)
		);
	}

	@Override
	public void run(String... args) throws Exception {
		exitCode = new CommandLine(baseCommand, iFactory).execute(args);
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}
}
