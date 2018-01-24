package src;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class Log {

    private static Log logger = new Log();

    private PublishSubject<String> messages = PublishSubject.create();


    private Log() {
        messages.subscribeOn(Schedulers.io()).doOnNext(System.out::println).subscribe();
    }

    public static void print(String message) {
//        logger.messages.onNext(message);
        System.out.println(message);
    }

    public static void debug(String s) {
        if (Constants.DEBUG) {
            print(s);
        }
    }
}
