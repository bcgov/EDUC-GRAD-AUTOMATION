package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.exception.TraxBatchServiceException;
import ca.bc.gov.educ.gtts.filters.ListFilters;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.educ.gtts.model.dto.students.api.GraduationStudentRecord;
import ca.bc.gov.educ.gtts.model.entity.TswTranDemogEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;
import ca.bc.gov.educ.gtts.model.transformer.TraxGradComparisonTransformer;
import org.javers.core.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ca.bc.gov.educ.gtts.filters.ListFilters.filterSccpAndNonGradPrograms;

import java.util.List;
import java.util.function.Predicate;

@Service
public class TraxBatchServiceImpl implements TraxBatchService {

    private GradService gradService;
    private TraxService traxService;
    private TraxGradComparisonTransformer traxGradComparisonTransformer;
    private ComparatorService comparatorService;
    private ReportService reportService;
    private ListFilters listFilters;

    @Autowired
    public TraxBatchServiceImpl(GradService gradService, TraxService traxService, TraxGradComparisonTransformer traxGradComparisonTransformer, ComparatorService comparatorService, ReportService reportService, ListFilters listFilters) {
        this.gradService = gradService;
        this.traxService = traxService;
        this.traxGradComparisonTransformer = traxGradComparisonTransformer;
        this.comparatorService = comparatorService;
        this.reportService = reportService;
        this.listFilters = listFilters;
    }

    @Override
    public void runTest(List<String> pens) throws TraxBatchServiceException {
        try {
            for (String pen : pens) {
                System.out.print("Processing: " + pen);
                // get info from TRAX
                TraxGradComparatorDto traxGradComparatorDtoFromTrax = getTraxGradComparatorDtoFromTrax(pen);
                // fetch info from GRAD
                TraxGradComparatorDto traxGradComparatorDtoFromGRAD = getTraxGradComparatorDtoFromGradAlgorithm(pen, traxGradComparatorDtoFromTrax);
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

        } catch (GenericHTTPRequestServiceException | NotFoundException e) {
            throw new TraxBatchServiceException(e.getMessage());
        }
    }

    @Override
    public void runTest() throws TraxBatchServiceException {
        List<GraduationStudentRecord> graduationStudentRecords = getGraduationStudentRecordList(
                filterSccpAndNonGradPrograms()
        );
        testRunner(graduationStudentRecords);
    }


    @Override
    public void runTest(Predicate<GraduationStudentRecord> optionalFilter) throws TraxBatchServiceException {
        List<GraduationStudentRecord> graduationStudentRecords = getGraduationStudentRecordList(
                filterSccpAndNonGradPrograms()
        );
        if(optionalFilter != null){
            graduationStudentRecords = listFilters.filterGraduationStudentRecordList(optionalFilter, graduationStudentRecords);
        }
        testRunner(graduationStudentRecords);
    }

    /**
     * Actually does the heavy lifting
     * @param graduationStudentRecords
     * @return
     */
    private void testRunner(List<GraduationStudentRecord> graduationStudentRecords){
        System.out.println("Processing " + graduationStudentRecords.size() + " records found.");
        for (GraduationStudentRecord record : graduationStudentRecords) {
            System.out.println("processing: " + record.getStudentID());
            try {
                GradSearchStudent gradSearchStudent = gradService.getStudentByID(record.getStudentID().toString());
                TraxGradComparatorDto traxGradComparatorDtoFromTrax = getTraxGradComparatorDtoFromTrax(gradSearchStudent.getPen());
                TraxGradComparatorDto traxGradComparatorDtoFromGrad = getTraxGradComparatorDtoFromGradAlgorithm(record, traxGradComparatorDtoFromTrax);
                Diff diffs = comparatorService.compareTraxGradDTOs(traxGradComparatorDtoFromTrax, traxGradComparatorDtoFromGrad);
                // if diffs, report
                if(diffs.hasChanges()){
                    System.out.println("");
                    reportService.reportDifferences(gradSearchStudent.getPen(), diffs);
                } else {
                    System.out.println(" -- No differences.");
                }
            } catch (NotFoundException | GenericHTTPRequestServiceException e) {
                System.out.println("Failed to process: " + record.getStudentID() + " due to: " + e.getMessage());
                continue;
            }
        }
    }

    /**
     * Retrieves a list of student records that can be run through projected GRAD algo
     * @param optionalFilter an optional predicate filter. Pass null if not interested in filtering
     * @return filtered or non-filtered list
     * @throws TraxBatchServiceException
     */
    private List<GraduationStudentRecord> getGraduationStudentRecordList(Predicate<GraduationStudentRecord> optionalFilter) throws TraxBatchServiceException {
        List<GraduationStudentRecord> graduationStudentRecords = null;
        try {
            graduationStudentRecords = gradService.getProjectedGradStudentList();
        } catch (GenericHTTPRequestServiceException | NotFoundException e) {
            throw new TraxBatchServiceException("Failure to retrieve pens to process: " + e.getMessage());
        }
        return (optionalFilter != null) ? listFilters.filterGraduationStudentRecordList(optionalFilter, graduationStudentRecords) : graduationStudentRecords;
    }


    /**
     * Convenience method for retrieving a TraxGradComparatorDTO from TRAX data
     * @param pen
     * @return
     * @throws NotFoundException
     */
    private TraxGradComparatorDto getTraxGradComparatorDtoFromTrax(String pen) throws NotFoundException {
        TswTranDemogEntity tswTranDemogEntity = traxService.getTransDemogEntity(pen);
        if(tswTranDemogEntity == null){
            throw new NotFoundException("Student with pen: " + pen + " not found in TRAX");
        }
        List<TswTranNonGradEntity> nonGradReasons = traxService.getNonGradReasons(pen);
        return traxGradComparisonTransformer.getTraxGradComparatorDto(tswTranDemogEntity, nonGradReasons);
    }

    /**
     * Convenience method for creating a TraxGradComparatorDTO from GRAD
     * @param record
     * @return
     * @throws NotFoundException
     * @throws GenericHTTPRequestServiceException
     */
    private TraxGradComparatorDto getTraxGradComparatorDtoFromGradAlgorithm(GraduationStudentRecord record, TraxGradComparatorDto traxGradComparatorDtoFromTrax) throws NotFoundException, GenericHTTPRequestServiceException {
        GraduationData projectedGraduationData = gradService.runProjectedGraduation(record.getStudentID().toString(), record.getProgram());
        return traxGradComparisonTransformer.getTraxGradComparatorDto(projectedGraduationData, traxGradComparatorDtoFromTrax);
    }

    /**
     * Convenience method for creating a TraxGradComparatorDTO from GRAD
     * @param pen
     * @return
     * @throws NotFoundException
     * @throws GenericHTTPRequestServiceException
     */
    private TraxGradComparatorDto getTraxGradComparatorDtoFromGradAlgorithm(String pen, TraxGradComparatorDto traxGradComparatorDtoFromTrax) throws NotFoundException, GenericHTTPRequestServiceException {
        GradSearchStudent gradSearchStudent = gradService.getStudentByPen(pen);
        GraduationData projectedGraduationData = gradService.runProjectedGraduation(gradSearchStudent.getStudentID(), gradSearchStudent.getProgram());
        return traxGradComparisonTransformer.getTraxGradComparatorDto(projectedGraduationData, traxGradComparatorDtoFromTrax);
    }

}
