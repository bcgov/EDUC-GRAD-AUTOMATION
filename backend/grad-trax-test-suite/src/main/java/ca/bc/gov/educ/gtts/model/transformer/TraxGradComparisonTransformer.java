package ca.bc.gov.educ.gtts.model.transformer;

import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorNonGradDto;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GradRequirement;
import ca.bc.gov.educ.gtts.model.dto.grad.algorithm.GraduationData;
import ca.bc.gov.educ.gtts.model.entity.TswTranDemogEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Transforms TRAX and GRAD produced beans into comparator model format
 */
@Component
public class TraxGradComparisonTransformer {

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
     * Creates a comparator object from GRAD data
     * @param graduationData
     * @return
     */
    public TraxGradComparatorDto getTraxGradComparatorDto(GraduationData graduationData){
        TraxGradComparatorDto traxGradComparatorDto = new TraxGradComparatorDto();
        traxGradComparatorDto.setPen(graduationData.getGradStudent().getPen());
        traxGradComparatorDto.setGradReqtYear(
                // GRAD_REQUT_YEAR in TRAX is just the year, but in GRAD
                // it is mixed with other information, for example 2004-EN
                // we remove any non-numerics to match the data from trax
                graduationData.getGradStudent().getProgram().replaceAll("[^x0-9]", "")
        );
        traxGradComparatorDto.setGradDate(
                // GRAD_DATE in TRAX is just YYYYMM or 0 if non grad, whereas GRAD is YYYY-MM-DD or null
                // we set the date to 0 if null in GRAD and remove all '-' and truncate to match the TRAX data
                (graduationData.getGradStatus().getProgramCompletionDate() == null)
                        ? "0"
                        : graduationData.getGradStatus().getProgramCompletionDate()
                        .replaceAll("-", "")
                        .substring(0, 6)
        );
        traxGradComparatorDto.setGradFlag(
                (graduationData.isGraduated()) ? "Y" : "N"
        );
        if(graduationData.getNonGradReasons() != null){
            for (GradRequirement gradRequirement : graduationData.getNonGradReasons()) {
                traxGradComparatorDto.getTraxGradComparatorNonGradDtoList().add(new TraxGradComparatorNonGradDto(
                        graduationData.getGradStudent().getPen(),
                        gradRequirement.getRule(),
                        gradRequirement.getDescription()
                ));
            }
        }

        return traxGradComparatorDto;
    }

}
