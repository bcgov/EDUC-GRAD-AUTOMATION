package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.GradSearchStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean runTest() {
        try {
            traxService.findTraxStudentByPEN("107223315");
            GradSearchStudent gradSearchStudent = gradService.getStudentByPen("107223315");
            System.out.println("Pen is: " + gradSearchStudent.getPen());
        } catch (GenericHTTPRequestServiceException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
