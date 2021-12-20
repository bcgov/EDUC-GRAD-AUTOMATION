package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;
import ca.bc.gov.educ.gtts.model.entity.TraxStudentEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranDemogEntity;
import ca.bc.gov.educ.gtts.model.entity.TswTranNonGradEntity;
import ca.bc.gov.educ.gtts.model.transformer.TraxStudentTransformer;
import ca.bc.gov.educ.gtts.repository.TraxStudentRepository;
import ca.bc.gov.educ.gtts.repository.TraxTswTranDemogRepository;
import ca.bc.gov.educ.gtts.repository.TswTranNonGradRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author CDITCHER
 * Service class for interacting with TRAX data
 */
@Service
public class TraxServiceImpl implements TraxService {

    private TraxStudentRepository repository;
    private TswTranNonGradRepository tswTranNonGradRepository;
    private TraxTswTranDemogRepository traxTswTranDemogRepository;
    private TraxStudentTransformer transformer;

    @Autowired
    public TraxServiceImpl(
            TraxStudentRepository repository,
            TraxStudentTransformer transformer,
            TswTranNonGradRepository tswTranNonGradRepository,
            TraxTswTranDemogRepository traxTswTranDemogRepository) {
        this.repository = repository;
        this.transformer = transformer;
        this.tswTranNonGradRepository = tswTranNonGradRepository;
        this.traxTswTranDemogRepository = traxTswTranDemogRepository;
    }

    /**
     * Finds student entities from TRAX STUDENT_MASTER
     * @param pen a valid pen number (mapped to STUD_NO)
     * @return TraxStudentDto if found
     * @throws NotFoundException
     */
    @Override
    public TraxStudentDto findTraxStudentByPEN(String pen) throws NotFoundException {
        TraxStudentEntity student = repository.findById(pen).orElse(null);
        if(student == null){
            throw new NotFoundException("Student with pen: " + pen + " cannot be found in TRAX");
        }
        return transformer.transformToDTO(student);
    }

    /**
     * Returns non-grad reasons from TRAX TSW_NON_GRAD schema
     * @param pen the pen (or STUD_NO in TRAX) of the student
     * @return an Iterable of found students (zero length array if none found)
     */
    private Iterable<TswTranNonGradEntity> getNonGradReasons(String pen){
        return tswTranNonGradRepository.findByPen(pen);
    }

    /**
     * Returns student data from the TRAX TSW_TRAN_DEMOG schema
     * @param pen a valid pen
     * @return TswTranDemogEntity
     */
    private TswTranDemogEntity getTransDemogEntity(String pen){
        return traxTswTranDemogRepository.findById(pen).orElse(null);
    }

}
