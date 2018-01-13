package src.data;

import src.base.Exchange;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class ExchangeConnector<T extends Exchange> {

    private final BehaviorSubject<T> subject = BehaviorSubject.create();

    public Flowable<T> asFlowable() {
        return subject.toFlowable(BackpressureStrategy.LATEST);
    }

    protected final void onExchangeUpdated(T exchange) {
        subject.onNext(exchange);
    }

    public abstract void start();


    public void test(T exchange) {
        onExchangeUpdated(exchange);
    }

}
