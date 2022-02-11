package ca.bc.gov.gradtraxcomparisontest.reactive;

import ca.bc.gov.gradtraxcomparisontest.model.CompareWithTraxResponse;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiffSubscriber implements Subscriber<CompareWithTraxResponse> {

    private ApplicationContext context;

    @Autowired
    public DiffSubscriber(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("Subscription started.");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(CompareWithTraxResponse response) {
            if(response.getResponseCode() == 200){
                System.out.println(response.getStudentId() + " : no changes.");
            } else if(response.getResponseCode() == 404) {
                System.out.println(response.getStudentId() + " : was not found.");
            } else if(response.getResponseCode() == 500) {
                System.out.println(response.getStudentId() + " : ERROR: " + response.getMessage());
            } else {
                System.out.println(response.getStudentId() + " : Changes: " + response.getMessage());
            }
        //System.out.println("Processed: " + strings.toString() + " on thread " + Thread.currentThread().getName());
    }


    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on subscriber: ");
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        // TDOO: find more graceful shutdown method
        System.out.println("Subscription complete. Shutting down.");
        SpringApplication.exit(context);
    }
}