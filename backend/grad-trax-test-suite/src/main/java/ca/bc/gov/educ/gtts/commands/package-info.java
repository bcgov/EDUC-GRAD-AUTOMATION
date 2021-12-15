/**
 * @author CDITCHER
 * This package contains classes that are responsible for responding
 * to the various commands passed into the application. Command classes
 * are named after their command, so for example if the argument given to
 * the application is 'gradt integration {params}', then you would find the
 * classe called 'IntegrationCommand' and its methods and fields would correspond to
 * the arguments passed in. The application uses picocli library to make
 * building commands easier (https://www.baeldung.com/java-picocli-create-command-line-program)
 */
package ca.bc.gov.educ.gtts.commands;