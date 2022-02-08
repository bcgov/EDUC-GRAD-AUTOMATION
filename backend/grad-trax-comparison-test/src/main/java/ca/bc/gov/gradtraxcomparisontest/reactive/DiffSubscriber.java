package ca.bc.gov.gradtraxcomparisontest.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiffSubscriber implements Subscriber<List<String>> {

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
    public void onNext(List<String> strings) {
        System.out.println("Processed: " + strings.toString() + " on thread " + Thread.currentThread().getName());
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