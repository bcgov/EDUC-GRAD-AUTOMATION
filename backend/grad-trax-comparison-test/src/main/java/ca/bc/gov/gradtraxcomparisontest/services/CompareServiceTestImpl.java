package ca.bc.gov.gradtraxcomparisontest.services;

import ca.bc.gov.gradtraxcomparisontest.reactive.DiffSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class CompareServiceTestImpl implements CompareServiceTest {

    WebClient webClient = WebClient.create("http://localhost:8080");
    DiffSubscriber diffSubscriber;

    @Autowired
    public CompareServiceTestImpl(DiffSubscriber diffSubscriber) {
        this.diffSubscriber = diffSubscriber;
    }

    @Override
    public void beginTest() {
        List<String> ids = Arrays.asList(
                new String[]{"one", "two", "three"}
        );
        fetch(ids).subscribe(diffSubscriber);
    }


    private Mono<String> getInfo(String id){
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }

    private Flux fetch(List<String> ids) {
        return Flux
                .fromIterable(ids)
                .flatMap(this::getInfo)
                .buffer(2);
    }

}
