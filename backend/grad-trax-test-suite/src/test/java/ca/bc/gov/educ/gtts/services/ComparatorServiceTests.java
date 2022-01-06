package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorDto;
import ca.bc.gov.educ.gtts.model.dto.TraxGradComparatorNonGradDto;
import org.javers.core.diff.Diff;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ComparatorServiceTests {

    @Autowired
    ComparatorService comparatorService;
    @Autowired
    ReportService reportService;

    public ComparatorServiceTests() {
    }

    @Test
    public void testCompareIdentical(){
        TraxGradComparatorDto traxGradComparatorDto = getTraxGradComparatorDtoOne();
        Diff diff = comparatorService.compareTraxGradDTOs(traxGradComparatorDto, traxGradComparatorDto);
        Assert.assertTrue(!diff.hasChanges());
    }

    @Test
    public void testCompareNonIdentical(){
        Diff diff = comparatorService.compareTraxGradDTOs(getTraxGradComparatorDtoOne(), getTraxGradComparatorDtoTwo());
        Assert.assertTrue(diff.hasChanges());
    }

    private static TraxGradComparatorDto getTraxGradComparatorDtoOne(){
        String pen = getRandomPen();
        TraxGradComparatorDto traxGradComparatorDto = new TraxGradComparatorDto();
        traxGradComparatorDto.setPen(pen);
        traxGradComparatorDto.setGradReqtYear("2010");
        traxGradComparatorDto.setGradDate("20102312");
        traxGradComparatorDto.setGradFlag("true");
        traxGradComparatorDto.setUpdateDate(1985);
        traxGradComparatorDto.setTraxGradComparatorNonGradDtoList(getGradComparatorNonGradDtoList(3, pen));
        return traxGradComparatorDto;
    }

    private static TraxGradComparatorDto getTraxGradComparatorDtoTwo(){
        String pen = getRandomPen();
        TraxGradComparatorDto traxGradComparatorDto = new TraxGradComparatorDto();
        traxGradComparatorDto.setPen(pen);
        traxGradComparatorDto.setGradReqtYear("2010");
        traxGradComparatorDto.setGradDate("20102311");
        traxGradComparatorDto.setGradFlag("true");
        traxGradComparatorDto.setUpdateDate(1986);
        traxGradComparatorDto.setTraxGradComparatorNonGradDtoList(getGradComparatorNonGradDtoList(3, pen));
        return traxGradComparatorDto;
    }

    private static List<TraxGradComparatorNonGradDto> getGradComparatorNonGradDtoList(int size, String pen){
        List<TraxGradComparatorNonGradDto> list = new ArrayList<>();
        for (int i = 0; i < size-1; i++) {
            list.add(getTraxGradComparatorNonGradDto(pen, "700"));
        }
        return list;
    }

    private static TraxGradComparatorNonGradDto getTraxGradComparatorNonGradDto(String pen, String nonGradCode){
        return new TraxGradComparatorNonGradDto(pen, nonGradCode);
    }

    private static String getRandomPen(){
        return Integer.toString(ThreadLocalRandom.current().nextInt(100000000, 999999999));
    }
}
