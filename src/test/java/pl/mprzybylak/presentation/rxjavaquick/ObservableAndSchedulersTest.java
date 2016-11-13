package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
    public void observeOnDifferentThread() throws InterruptedException {

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

        Thread.sleep(100);

        // then
        assertThat(mainThread.get().getId()).isEqualTo(mainThread.get().getId());
        assertThat(switchedThread.get().getId()).isNotEqualTo(mainThread.get().getId());
    }

    @Test
    public void orderedSequenceOfNumbersWithGivenInterval() throws InterruptedException {

        // given
        CountDownLatch latch = new CountDownLatch(1);
        List<Integer> sequence = new ArrayList<>(10);
        LocalDateTime start = LocalDateTime.now();

        // when
        Observable.intervalRange(0, 100, 0, 10, TimeUnit.MILLISECONDS)
                .subscribe(l -> {
                }, t -> {
                }, latch::countDown);

        latch.await();

        LocalDateTime end = LocalDateTime.now();

        // then
        int difference = (int)start.until(end, ChronoUnit.MILLIS);
        assertThat(difference).isBetween(900, 1200);

    }

    @Test
    public void interval() throws InterruptedException {

        // given
        List<Long> results = new ArrayList<>(10);

        // when
        Observable.interval(3, 1, TimeUnit.MILLISECONDS)
                .take(10)
                .subscribe(results::add);

        Thread.sleep(1000);

        // then
        assertThat(results.get(0)).isEqualTo(0);
        assertThat(results.get(1)).isEqualTo(1);
        assertThat(results.get(2)).isEqualTo(2);
        assertThat(results.get(3)).isEqualTo(3);
        assertThat(results.get(4)).isEqualTo(4);
        assertThat(results.get(5)).isEqualTo(5);
        assertThat(results.get(6)).isEqualTo(6);
        assertThat(results.get(7)).isEqualTo(7);
        assertThat(results.get(8)).isEqualTo(8);
        assertThat(results.get(9)).isEqualTo(9);
    }

    @Test
    public void timer() throws InterruptedException {

        // given
        CountDownLatch latch = new CountDownLatch(1);
        LocalDateTime start = LocalDateTime.now();
        AtomicReference<LocalDateTime> ends = new AtomicReference<>();

        // wait
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(aLong -> ends.set(LocalDateTime.now()),
                        throwable -> {
                        },
                        latch::countDown
                );
        latch.await();

        // then
        int difference = (int)start.until(ends.get(), ChronoUnit.SECONDS);
        assertThat(difference).isBetween(1, 2);
    }
}
