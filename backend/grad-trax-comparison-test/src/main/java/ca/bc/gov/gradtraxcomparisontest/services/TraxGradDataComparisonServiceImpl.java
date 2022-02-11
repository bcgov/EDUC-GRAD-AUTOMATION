package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;
import ca.bc.gov.gradtraxcomparisontest.model.dto.TraxGradComparatorDto;
import ca.bc.gov.gradtraxcomparisontest.model.dto.GradSearchStudent;
import ca.bc.gov.gradtraxcomparisontest.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.gradtraxcomparisontest.model.entity.TswTranDemogEntity;
import ca.bc.gov.gradtraxcomparisontest.model.entity.TswTranNonGradEntity;
import ca.bc.gov.gradtraxcomparisontest.transformer.TraxGradComparisonTransformer;
import org.javers.core.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraxGradDataComparisonServiceImpl implements TraxGradDataComparisonService {

    GradService gradService;
    TraxService traxService;
    ComparatorService comparatorService;
    TraxGradComparisonTransformer traxGradComparisonTransformer;

    @Autowired
    public TraxGradDataComparisonServiceImpl(GradService gradService, TraxService traxService, ComparatorService comparatorService, TraxGradComparisonTransformer traxGradComparisonTransformer) {
        this.gradService = gradService;
        this.traxService = traxService;
        this.comparatorService = comparatorService;
        this.traxGradComparisonTransformer = traxGradComparisonTransformer;
    }

    @Override
    public Diff compareWithTraxData(String studentId, String program) throws GenericHTTPRequestServiceException, NotFoundException {
        GradSearchStudent gradSearchStudent = gradService.getStudentByID(studentId);
        TraxGradComparatorDto traxGradComparatorDtoFromTrax = getTraxGradComparatorDtoFromTrax(gradSearchStudent.getPen());
        TraxGradComparatorDto traxGradComparatorDtoFromGrad = getTraxGradComparatorDtoFromGradAlgorithm(studentId, program, traxGradComparatorDtoFromTrax);
        Diff diffs = comparatorService.compareTraxGradDTOs(traxGradComparatorDtoFromTrax, traxGradComparatorDtoFromGrad);
        return diffs;
    }

    private TraxGradComparatorDto getTraxGradComparatorDtoFromTrax(String pen) throws NotFoundException {
        TswTranDemogEntity tswTranDemogEntity = traxService.getTransDemogEntity(pen);
        if(tswTranDemogEntity == null){
            throw new NotFoundException("Student with pen: " + pen + " not found in TRAX");
        }
        List<TswTranNonGradEntity> nonGradReasons = traxService.getNonGradReasons(pen);
        return traxGradComparisonTransformer.getTraxGradComparatorDto(tswTranDemogEntity, nonGradReasons);
    }

    private TraxGradComparatorDto getTraxGradComparatorDtoFromGradAlgorithm(String studentId, String program, TraxGradComparatorDto traxGradComparatorDtoFromTrax) throws NotFoundException, GenericHTTPRequestServiceException {
        GraduationData projectedGraduationData = gradService.runProjectedGraduation(studentId, program);
        return traxGradComparisonTransformer.getTraxGradComparatorDto(projectedGraduationData, traxGradComparatorDtoFromTrax);
    }


}
