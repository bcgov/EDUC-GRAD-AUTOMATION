package ca.bc.gov.gradtraxtestclient.services;

import ca.bc.gov.gradtraxtestclient.exception.GenericHTTPRequestServiceException;
import ca.bc.gov.gradtraxtestclient.exception.NotFoundException;
import ca.bc.gov.gradtraxtestclient.exception.TraxBatchServiceException;
import ca.bc.gov.gradtraxtestclient.filters.ListFilters;
import ca.bc.gov.gradtraxtestclient.model.dto.students.api.GraduationStudentRecord;
import ca.bc.gov.gradtraxtestclient.react.DiffSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ca.bc.gov.gradtraxtestclient.filters.ListFilters.filterSccpAndNonGradPrograms;

@Service
public class TestService {

    WebClient webClient;
    DiffSubscriber diffSubscriber;
    GradService gradService;
    ListFilters listFilters;

    @Autowired
    public TestService(@Qualifier("reactiveWebClient") WebClient webClient, DiffSubscriber diffSubscriber, GradService gradService, ListFilters listFilters) {
        this.webClient = webClient;
        this.diffSubscriber = diffSubscriber;
        this.gradService = gradService;
        this.listFilters = listFilters;
    }

    public void beginTest() throws TraxBatchServiceException {
        // get Student Records from GRAD
        List<GraduationStudentRecord> graduationStudentRecords = getGraduationStudentRecordList(
                filterSccpAndNonGradPrograms()
        );
        // Get list of ids for processing
        List<UUID> ids = graduationStudentRecords.stream()
                .map(GraduationStudentRecord::getStudentID)
                .collect(Collectors.toList());
        // fetch and subscribe
        this.fetch(ids).subscribe(diffSubscriber);
    };


    private Mono<String> getInfo(UUID id){
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }

    private Flux fetch(List<UUID> ids) {
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
