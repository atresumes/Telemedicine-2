package rx.internal.operators;

import java.util.concurrent.Callable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.internal.producers.SingleDelayedProducer;

public final class OnSubscribeFromCallable<T> implements OnSubscribe<T> {
    private final Callable<? extends T> resultFactory;

    public OnSubscribeFromCallable(Callable<? extends T> resultFactory) {
        this.resultFactory = resultFactory;
    }

    public void call(Subscriber<? super T> subscriber) {
        SingleDelayedProducer<T> singleDelayedProducer = new SingleDelayedProducer(subscriber);
        subscriber.setProducer(singleDelayedProducer);
        try {
            singleDelayedProducer.setValue(this.resultFactory.call());
        } catch (Throwable t) {
            Exceptions.throwOrReport(t, (Observer) subscriber);
        }
    }
}
