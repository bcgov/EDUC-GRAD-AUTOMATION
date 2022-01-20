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
    public boolean runTest(List<String> pens) throws TraxBatchServiceException {
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

        } catch (GenericHTTPRequestServiceException | NotFoundException e) {
            throw new TraxBatchServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean runTest() throws TraxBatchServiceException {
        List<GraduationStudentRecord> graduationStudentRecords = getGraduationStudentRecordList(
                filterSccpAndNonGradPrograms()
        );
            for (GraduationStudentRecord record : graduationStudentRecords) {
                System.out.println("processing: " + record.getStudentID());
                try {
                    TraxGradComparatorDto traxGradComparatorDtoFromGrad = getTraxGradComparatorDtoFromGradAlgorithm(record);
                    GradSearchStudent gradSearchStudent = gradService.getStudentByID(record.getStudentID().toString());
                    TraxGradComparatorDto traxGradComparatorDtoFromTrax = getTraxGradComparatorDtoFromTrax(gradSearchStudent.getPen());
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

        return false;
    }



    @Override
    public boolean runTest(Predicate<GraduationStudentRecord> optionalFilter) throws TraxBatchServiceException {
        List<GraduationStudentRecord> graduationStudentRecords = getGraduationStudentRecordList(
                filterSccpAndNonGradPrograms()
        );
        if(optionalFilter != null){
            graduationStudentRecords = listFilters.filterGraduationStudentRecordList(optionalFilter, graduationStudentRecords);
        }
        return false;
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
     * Convenience
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

    private TraxGradComparatorDto getTraxGradComparatorDtoFromGradAlgorithm(GraduationStudentRecord record) throws NotFoundException, GenericHTTPRequestServiceException {
        GraduationData projectedGraduationData = gradService.runProjectedGraduation(record.getStudentID().toString(), record.getProgram());
        return traxGradComparisonTransformer.getTraxGradComparatorDto(projectedGraduationData);
    }

    private TraxGradComparatorDto getTraxGradComparatorDtoFromGradAlgorithm(String pen) throws NotFoundException, GenericHTTPRequestServiceException {
        GradSearchStudent gradSearchStudent = gradService.getStudentByPen(pen);
        GraduationData projectedGraduationData = gradService.runProjectedGraduation(gradSearchStudent.getStudentID(), gradSearchStudent.getProgram());
        return traxGradComparisonTransformer.getTraxGradComparatorDto(projectedGraduationData);
    }

}
