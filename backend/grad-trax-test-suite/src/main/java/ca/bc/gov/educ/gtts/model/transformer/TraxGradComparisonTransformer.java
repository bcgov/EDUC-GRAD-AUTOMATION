package ca.bc.gov.educ.gtts.model.transformer;

import ca.bc.gov.educ.gtts.config.GttsProperties;
import ca.bc.gov.educ.gtts.model.config.Program;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorNonGradDto;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GradRequirement;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.educ.gtts.model.entity.TswTranDemogEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Transforms TRAX and GRAD produced beans into comparator model format
 */
@Component
public class TraxGradComparisonTransformer {

    GttsProperties properties;

    @Autowired
    public TraxGradComparisonTransformer(GttsProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates comparator dto for TRAX data
     * @param tswTranDemogEntity
     * @param tswTranNonGradEntites
     * @return
     */
    public TraxGradComparatorDto getTraxGradComparatorDto(TswTranDemogEntity tswTranDemogEntity, List<TswTranNonGradEntity> tswTranNonGradEntites){
        TraxGradComparatorDto traxGradComparatorDto = new TraxGradComparatorDto();
        traxGradComparatorDto.setGradDate(tswTranDemogEntity.getGradDate().trim());
        traxGradComparatorDto.setGradFlag(tswTranDemogEntity.getGradFlag());
        traxGradComparatorDto.setGradReqtYear(tswTranDemogEntity.getGradReqtYear());
        traxGradComparatorDto.setPen(tswTranDemogEntity.getStudNo());
        for (TswTranNonGradEntity tswTranNonGradEntity : tswTranNonGradEntites) {
            traxGradComparatorDto.getTraxGradComparatorNonGradDtoList().add(new TraxGradComparatorNonGradDto(
                    tswTranNonGradEntity.getStudNo().trim(),
                    tswTranNonGradEntity.getNonGradCode().trim(),
                    tswTranNonGradEntity.getNonGradDescription().trim()
            ));
        }
        return traxGradComparatorDto;
    }

    /**
     * Creates a comparator object from GRAD data, the object is formatted
     * to conform with the data coming from TRAX using special filters.
     * A call MUST be made to TRAX FIRST to populate a TraxGradComparatorDto
     * as data from this object is used to create the returned TraxGradComparatorDto
     * @param graduationData
     * @return
     */
    public TraxGradComparatorDto getTraxGradComparatorDto(GraduationData graduationData, TraxGradComparatorDto traxGradComparatorDtoFromTrax){
        List<Program> mappings = properties.getRequirementMappings();
        TraxGradComparatorDto traxGradComparatorDto = new TraxGradComparatorDto();
        traxGradComparatorDto.setPen(graduationData.getGradStudent().getPen());
        traxGradComparatorDto.setGradReqtYear(transformGradReqtYear(graduationData));
        traxGradComparatorDto.setGradDate(transformGradDate(graduationData));
        traxGradComparatorDto.setGradFlag(
                (graduationData.isGraduated()) ? "Y" : "N"
        );
        if(graduationData.getNonGradReasons() != null){
            for (GradRequirement gradRequirement : graduationData.getNonGradReasons()) {
                // TODO use mapping logic on these non grad reasons
                traxGradComparatorDto.getTraxGradComparatorNonGradDtoList().add(new TraxGradComparatorNonGradDto(
                        graduationData.getGradStudent().getPen(),
                        gradRequirement.getRule(),
                        gradRequirement.getDescription()
                ));
            }
        }

        return traxGradComparatorDto;
    }

    private static String transformGradReqtYear(GraduationData graduationData) {
        // GRAD_REQUT_YEAR in TRAX is just the year, but in GRAD
        // it is mixed with other information, for example 2004-EN
        // we remove any non-numerics to match the data from trax
        return graduationData.getGradStudent().getProgram().replaceAll("[^x0-9]", "");
    }

    private static String transformGradDate(GraduationData graduationData){
        // GRAD_DATE in TRAX is just YYYYMM or 0 if non grad, whereas GRAD is YYYY-MM-DD or null
        // we set the date to 0 if null in GRAD and remove all '-' and truncate to match the TRAX data
        return (graduationData.getGradStatus().getProgramCompletionDate() == null)
                ? "0"
                : graduationData.getGradStatus().getProgramCompletionDate()
                .replaceAll("-", "")
                .substring(0, 6);
    }

}
