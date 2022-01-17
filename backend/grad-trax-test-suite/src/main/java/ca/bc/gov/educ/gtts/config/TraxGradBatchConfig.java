package ca.bc.gov.educ.gtts.config;

import ca.bc.gov.educ.gtts.batch.GradTraxCompareProcessor;
import ca.bc.gov.educ.gtts.batch.GradTraxCompareReader;
import ca.bc.gov.educ.gtts.batch.GradTraxCompareWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Configures the TRAX/GRAD comparison batch
 */
@Configuration
@EnableBatchProcessing
public class TraxGradBatchConfig extends DefaultBatchConfigurer {

    JobRegistry jobRegistry;

    @Autowired
    public TraxGradBatchConfig(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        // override to do not set datasource even if a datasource exist.
        // initialize will use a Map based JobRepository (instead of database)
    }

    /*@Bean
    @Qualifier("traxGradBatchExecutor")
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }*/

    @Bean
    @Qualifier("traxGradBatchExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(2);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }

    @Bean
    public GradTraxCompareReader getGradTraxCompareReader() {
        //TestPens testPens = JSONUtilities.serializeJSONFileToObject(System.getProperty("TEST_PENS"), TestPens.class);
        List<String> testPens = Arrays.asList(
                new String[]{
                "117175729",
                "124781733",
                "124304700",
                "125999052",
                "126774009",
                "127518017",
                "127780997"
                });
        return new GradTraxCompareReader(testPens);
    }

    @Bean
    public GradTraxCompareProcessor getGradTraxCompareProcessor() {
        return new GradTraxCompareProcessor();
    }

    @Bean
    public AsyncItemProcessor<String, String> getAsyncItemProcessor(GradTraxCompareProcessor gradTraxCompareProcessor, @Qualifier("traxGradBatchExecutor") TaskExecutor taskExecutor) {
        AsyncItemProcessor<String, String> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(gradTraxCompareProcessor);
        //asyncItemProcessor.setTaskExecutor(taskExecutor);
        return asyncItemProcessor;
    }

    @Bean
    public GradTraxCompareWriter getGradTraxCompareWriter() {
        return new GradTraxCompareWriter();
    }

    @Bean
    public AsyncItemWriter<String> getAsyncItemWriter(GradTraxCompareWriter gradTraxCompareWriter){
        AsyncItemWriter<String> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(gradTraxCompareWriter);
        return asyncItemWriter;
    }

    @Bean
    public Step traxGradCompareStep(
            ItemReader<String> itemReader,
            AsyncItemProcessor<String, String> itemProcessor,
            AsyncItemWriter<String> itemWriter,
            StepBuilderFactory stepBuilderFactory,
            @Qualifier("traxGradBatchExecutor") TaskExecutor taskExecutor
    ){
        return stepBuilderFactory.get("traxGradCompareStep")
                .<String, Future<String>>chunk(1)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Job traxGradCompareJob(
            Step traxGradCompareStep,
            JobBuilderFactory jobBuilderFactory
    ){
        // TODO: add completion listener
        return jobBuilderFactory.get("traxGradCompareJob")
                .incrementer(new RunIdIncrementer())
                .flow(traxGradCompareStep)
                .end()
                .build();
    }
}
