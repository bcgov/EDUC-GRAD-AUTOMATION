package ca.bc.gov.educ.gtts.model.transformer;

import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorNonGradDto;
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
        traxGradComparatorDto.setGradDate(tswTranDemogEntity.getGradDate());
        traxGradComparatorDto.setGradFlag(tswTranDemogEntity.getGradFlag());
        traxGradComparatorDto.setGradReqtYear(tswTranDemogEntity.getGradReqtYear());
        traxGradComparatorDto.setPen(tswTranDemogEntity.getStudNo());
        traxGradComparatorDto.setUpdateDate(tswTranDemogEntity.getUpdateDate());
        for (TswTranNonGradEntity tswTranNonGradEntity : tswTranNonGradEntites) {
            traxGradComparatorDto.getTraxGradComparatorNonGradDtoList().add(new TraxGradComparatorNonGradDto(
                    tswTranNonGradEntity.getStudNo(),
                    tswTranNonGradEntity.getNonGradCode()
            ));
        }
        return traxGradComparatorDto;
    }

    public TraxGradComparatorDto getTraxGradComparatorDto(){
        TraxGradComparatorDto traxGradComparatorDto = new TraxGradComparatorDto();
        //TODO: finish this
        return traxGradComparatorDto;
    }

}
