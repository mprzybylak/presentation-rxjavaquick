package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableAndSchedulersTest {

    @Test
    public void subscriptionInDifferentThread() throws InterruptedException {

        // given
        AtomicReference<Thread> subscriptionThread = new AtomicReference<>();
        AtomicReference<Thread> mainThread = new AtomicReference<>();

        mainThread.set(Thread.currentThread());

        // when
        Observable.range(1, 10)
                .subscribeOn(Schedulers.computation())
                .subscribe(i -> {
                    subscriptionThread.set(Thread.currentThread());
                });

        Thread.sleep(100);

        assertThat(subscriptionThread.get().getId()).isNotEqualTo(mainThread.get().getId());
    }

    @Test
    public void observeOnDifferentThread() {

        // given
        AtomicReference<Thread> defaultThread = new AtomicReference<>();
        AtomicReference<Thread> mainThread = new AtomicReference<>();
        AtomicReference<Thread> switchedThread = new AtomicReference<>();

        // when
        mainThread.set(Thread.currentThread());

        Observable.just(1)
                .doOnEach(e -> defaultThread.set(Thread.currentThread()))
                .observeOn(Schedulers.computation())
                .doOnEach(e -> switchedThread.set(Thread.currentThread()))
                .subscribe();

        // then
        assertThat(mainThread.get().getId()).isEqualTo(mainThread.get().getId());
        assertThat(switchedThread.get().getId()).isNotEqualTo(mainThread.get().getId());
    }


}
