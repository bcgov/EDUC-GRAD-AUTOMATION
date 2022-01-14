package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.educ.gtts.model.entity.TswTranDemogEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;
import ca.bc.gov.educ.gtts.model.transformer.TraxGradComparisonTransformer;
import org.javers.core.diff.Diff;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraxBatchServiceImpl implements TraxBatchService {

    private GradService gradService;
    private TraxService traxService;
    private TraxGradComparisonTransformer traxGradComparisonTransformer;
    private ComparatorService comparatorService;
    private ReportService reportService;
    private JobLauncher jobLauncher;
    private Job traxGradCompareJob;

    private static final String TIME="time";
    private static final String JOB_TRIGGER="jobTrigger";
    private static final String JOB_TYPE="jobType";

    @Autowired
    public TraxBatchServiceImpl(GradService gradService, TraxService traxService, TraxGradComparisonTransformer traxGradComparisonTransformer, ComparatorService comparatorService, ReportService reportService, JobLauncher jobLauncher, Job traxGradCompareJob) {
        this.gradService = gradService;
        this.traxService = traxService;
        this.traxGradComparisonTransformer = traxGradComparisonTransformer;
        this.comparatorService = comparatorService;
        this.reportService = reportService;
        this.jobLauncher = jobLauncher;
        this.traxGradCompareJob = traxGradCompareJob;
    }

    @Override
    public boolean runTest(List<String> pens) {
        try {
            for (String pen : pens) {
                System.out.print("Processing: " + pen);
                // get info from TRAX
                TraxGradComparatorDto traxGradComparatorDtoFromTrax = getTraxGradComparatorDtoFromTrax(pen);
                // fetch info from GRAD
                TraxGradComparatorDto traxGradComparatorDtoFromGRAD = getTraxGradComparatorDtoFromGradAlgorithm(pen);
                // compare
                Diff diffs = comparatorService.compareTraxGradDTOs(traxGradComparatorDtoFromTrax, traxGradComparatorDtoFromGRAD);
                // if diffs, report
                if(diffs.hasChanges()){
                    System.out.println("");
                    reportService.reportDifferences(pen, diffs);
                } else {
                    System.out.println(" -- No differences.");
                }
            }

        } catch (GenericHTTPRequestServiceException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean runBatchTest() {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong(TIME, System.currentTimeMillis()).toJobParameters();
        builder.addString(JOB_TRIGGER, "BATCH");
        builder.addString(JOB_TYPE, "TRAXGRADCOMPAREBATCH");
        try {
            jobLauncher.run(traxGradCompareJob, builder.toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return true;
    }


    private TraxGradComparatorDto getTraxGradComparatorDtoFromTrax(String pen) throws NotFoundException {
        TswTranDemogEntity tswTranDemogEntity = traxService.getTransDemogEntity(pen);
        if(tswTranDemogEntity == null){
            throw new NotFoundException("Student with pen: " + pen + " not found in TRAX");
        }
        List<TswTranNonGradEntity> nonGradReasons = traxService.getNonGradReasons(pen);
        return traxGradComparisonTransformer.getTraxGradComparatorDto(tswTranDemogEntity, nonGradReasons);
    }

    private TraxGradComparatorDto getTraxGradComparatorDtoFromGradAlgorithm(String pen) throws NotFoundException, GenericHTTPRequestServiceException {
        GradSearchStudent gradSearchStudent = gradService.getStudentByPen(pen);
        GraduationData projectedGraduationData = gradService.runProjectedGraduation(gradSearchStudent.getStudentID(), gradSearchStudent.getProgram());
        return traxGradComparisonTransformer.getTraxGradComparatorDto(projectedGraduationData);
    }

}
