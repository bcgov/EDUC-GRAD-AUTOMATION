package ca.bc.gov.educ.gtts.services;

import ca.bc.gov.educ.gtts.exception.NotFoundException;
import ca.bc.gov.educ.gtts.model.dto.TraxStudentDto;
import ca.bc.gov.educ.gtts.model.entity.TraxStudentEntity;
import ca.bc.gov.educ.gtts.model.transformer.TraxStudentTransformer;
import ca.bc.gov.educ.gtts.repository.TraxStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TraxServiceImpl implements TraxService {

    private TraxStudentRepository repository;
    private TraxStudentTransformer transformer;

    @Autowired
    public TraxServiceImpl(TraxStudentRepository repository, TraxStudentTransformer transformer) {
        this.repository = repository;
        this.transformer = transformer;
    }

    @Override
    public TraxStudentDto findTraxStudentByPEN(String pen) throws NotFoundException {
        TraxStudentEntity student = repository.findById(pen).orElse(null);
        if(student == null){
            throw new NotFoundException("Student with pen: " + pen + " cannot be found in TRAX");
        }
        return transformer.transformToDTO(student);
    }

}
