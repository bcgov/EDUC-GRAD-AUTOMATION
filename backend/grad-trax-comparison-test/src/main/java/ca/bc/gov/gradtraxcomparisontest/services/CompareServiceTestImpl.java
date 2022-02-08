package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxcomparisontest.exception.NotFoundException;
import ca.bc.gov.gradtraxcomparisontest.exception.TraxBatchServiceException;
import ca.bc.gov.gradtraxcomparisontest.filters.ListFilters;
import ca.bc.gov.gradtraxcomparisontest.model.CompareWithTraxResponse;
import ca.bc.gov.gradtraxcomparisontest.model.dto.students.api.GraduationStudentRecord;
import ca.bc.gov.gradtraxcomparisontest.reactive.DiffSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static ca.bc.gov.gradtraxcomparisontest.filters.ListFilters.filterSccpAndNonGradPrograms;

@Service
public class CompareServiceTestImpl implements CompareServiceTest {

    WebClient webClient;
    DiffSubscriber diffSubscriber;
    ListFilters listFilters;
    GradService gradService;

    @Autowired
    public CompareServiceTestImpl(WebClient webClient, DiffSubscriber diffSubscriber, GradService gradService, ListFilters listFilters) {
        this.webClient = webClient;
        this.diffSubscriber = diffSubscriber;
        this.gradService = gradService;
        this.listFilters = listFilters;
    }

    @Override
    public void beginTest() throws TraxBatchServiceException {
        List<String> ids = Arrays.asList(
                new String[]{"one", "two", "three"}
        );
        // get list of students
        List<GraduationStudentRecord> graduationStudentRecords = getGraduationStudentRecordList(
                filterSccpAndNonGradPrograms()
        );
        fetch(ids).subscribe(diffSubscriber);
    }


    private Mono<CompareWithTraxResponse> getInfo(String id){
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(CompareWithTraxResponse.class);
    }

    private Flux fetch(List<String> ids) {
        return Flux
                .fromIterable(ids)
                .flatMap(this::getInfo)
                .buffer(2);
    }

    /**
     * Retrieves a list of student records that can be run through projected GRAD algo
     * @param optionalFilter an optional predicate filter. Pass null if not interested in filtering
     * @return filtered or non-filtered list
     * @throws TraxBatchServiceException
     */
    private List<GraduationStudentRecord> getGraduationStudentRecordList(Predicate<GraduationStudentRecord> optionalFilter) throws TraxBatchServiceException {
        List<GraduationStudentRecord> graduationStudentRecords = null;
        try {
            graduationStudentRecords = gradService.getProjectedGradStudentList();
        } catch (GenericHTTPRequestServiceException | NotFoundException e) {
            throw new TraxBatchServiceException("Failure to retrieve pens to process: " + e.getMessage());
        }
        return (optionalFilter != null) ? listFilters.filterGraduationStudentRecordList(optionalFilter, graduationStudentRecords) : graduationStudentRecords;
    }

}
