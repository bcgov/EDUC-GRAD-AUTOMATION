package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.student.GradSearchStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraxBatchServiceImpl implements TraxBatchService {

    private GradService gradService;
    private TraxDataService traxDataService;

    @Autowired
    public TraxBatchServiceImpl(GradService gradService, TraxDataService traxDataService) {
        this.gradService = gradService;
        this.traxDataService = traxDataService;
    }

    @Override
    public boolean runTest() {
        try {
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
