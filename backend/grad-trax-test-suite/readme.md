# grad-trax-test-suite

GRAD test suite is a command line application for processing various types of integration, performance and observability tests.

## Framework

This application is basically [Spring Boot](https://spring.io/projects/spring-boot) (without tomcat) wrapped in [picocli](https://picocli.info/) to make it an extensible command line utility.
This allows us to create a self-documenting utility with all the great features of Spring baked in.

## Requirements

Java 11
(See pom.xml for Maven dependencies)

## Project Structure

The project layout follows an opinionated layout standard to most Spring projects. In the `/src/main/java/ca.bc.gov.educ.gtts` directory
you will find the following, each with a package-info.java file:

| Directory | Notes |
|:----------|:------|
| commands | Top level command line classes. For example, the BaseCommand class responds to the first level `gradt` command entered on the command line. Top level commands delegate to lower commands using the subcommands parameter of the `@CommandLine.Command` annotation. See picocli [documentation](https://picocli.info/quick-guide.html) for more information. |
| config | Configuration files for the application. As is standard for Spring, an application.yml file located in `/src/main/resources` is where you will find static configuration which is parsed by classes in the config directory. Please note that there are many environment variables on which this application relies, some may contain sensitive data and should be supplied at runtime using secure means. For development purposes, an `.env` file may be placed at the project's root level directory with key=value pairs. |
| exception | Any project specific exception classes |
| io | Any classes relating to file based operations |
| model | Java beans (entities and DTOs) |
| repository | Spring data classes for database CRUD operations |
| services | Service layer classes |
| utilities | Utility helper classes for various situations (JSON helpers, etc) |

## Commands

When running from an IDE (like Eclipse or IntelliJ) the main class is `/src/main/java/ca.bc.gov.educ.gtts`. When running as a standalone
application, all commands start with `gradt` (although you can set whatever alias you like, see installation section).

For our purposes, we will use the standalone syntax with all commands prefixed with `gradt`.

As would be expected 

## Installation

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

```bash
pip install foobar
```

## Usage

```python
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)