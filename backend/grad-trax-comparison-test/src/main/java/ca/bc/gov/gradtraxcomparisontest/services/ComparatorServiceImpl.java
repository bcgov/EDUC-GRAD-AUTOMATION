package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.model.dto.TraxGradComparatorDto;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComparatorServiceImpl implements ComparatorService {

    private Javers javers;

    @Autowired
    public ComparatorServiceImpl(Javers javers) {
        this.javers = javers;
    }

    @Override
    public Diff compareTraxGradDTOs(TraxGradComparatorDto dto1, TraxGradComparatorDto dto2) {
        return javers.compare(dto1, dto2);
    }

}
