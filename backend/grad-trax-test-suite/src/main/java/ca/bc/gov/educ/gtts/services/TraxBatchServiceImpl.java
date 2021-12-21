package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraxBatchServiceImpl implements TraxBatchService {

    private GradService gradService;
    private TraxService traxService;

    @Autowired
    public TraxBatchServiceImpl(GradService gradService, TraxService traxService) {
        this.gradService = gradService;
        this.traxService = traxService;
    }

    @Override
    public boolean runTest(List<String> pens) {
        try {
            for (String pen : pens) {
                TraxStudentDto traxStudentDto = traxService.findTraxStudentByPEN(pen);
                GradSearchStudent gradSearchStudent = gradService.getStudentByPen(pen);
                System.out.println("TSDTO Pen is: " + gradSearchStudent.getPen() + " GSS Pen is: " + traxStudentDto.getStudNo());
            }

        } catch (GenericHTTPRequestServiceException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
