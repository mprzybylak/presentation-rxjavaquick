package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableCombineTest {

    @Test
    public void zipObservables() throws InterruptedException {

        // given
        List<Integer> combined = new ArrayList<>(10);

        // when
        Observable.zip(
                Observable.range(1, 10),
                Observable.range(20, 10),
                (first, sec) -> first + sec
        ).subscribe(combined::add);

        // then
        assertThat(combined.get(0)).isEqualTo(1 + 20);
        assertThat(combined.get(1)).isEqualTo(2 + 21);
        assertThat(combined.get(2)).isEqualTo(3 + 22);
        assertThat(combined.get(3)).isEqualTo(4 + 23);
        assertThat(combined.get(4)).isEqualTo(5 + 24);
        assertThat(combined.get(5)).isEqualTo(6 + 25);
        assertThat(combined.get(6)).isEqualTo(7 + 26);
        assertThat(combined.get(7)).isEqualTo(8 + 27);
        assertThat(combined.get(8)).isEqualTo(9 + 28);
        assertThat(combined.get(9)).isEqualTo(10 + 29);
    }

    @Test
    public void combineLatestValeuesFromTwoObservables() throws InterruptedException {

        // given
        List<Long> results = new ArrayList<>(9);

        Observable<Long> first = Observable.intervalRange(1,5, 0, 10, TimeUnit.MILLISECONDS);
        Observable<Long> second = Observable.intervalRange(1,5, 5, 10, TimeUnit.MILLISECONDS);

        // when
        Observable.combineLatest(first, second, (f, s) -> f + s)
        .subscribe(results::add);

        Thread.sleep(100);

        // then
        assertThat(results.get(0)).isEqualTo(1 + 1);
        assertThat(results.get(1)).isEqualTo(2 + 1);
        assertThat(results.get(2)).isEqualTo(2 + 2);
        assertThat(results.get(3)).isEqualTo(3 + 2);
        assertThat(results.get(4)).isEqualTo(3 + 3);
        assertThat(results.get(5)).isEqualTo(4 + 3);
        assertThat(results.get(6)).isEqualTo(4 + 4);
        assertThat(results.get(7)).isEqualTo(5 + 4);
        assertThat(results.get(8)).isEqualTo(5 + 5);

    }

    @Test
    public void merge() throws InterruptedException {

        // given
        List<Long> merged = Collections.synchronizedList(new ArrayList<>(10));

        // when
        Observable<Long> firstObservable = Observable.intervalRange(1, 2, 0, 10, TimeUnit.MILLISECONDS);
        Observable<Long> secondObservable = Observable.intervalRange(10, 2, 3, 10, TimeUnit.MILLISECONDS);

        Observable.merge(firstObservable, secondObservable)
                .subscribe(merged::add);

        Thread.sleep(20);

        // then
        assertThat(merged.get(0)).isEqualTo(1);
        assertThat(merged.get(1)).isEqualTo(10);
        assertThat(merged.get(2)).isEqualTo(2);
        assertThat(merged.get(3)).isEqualTo(11);
    }

    @Test
    public void concat() {

        List<Integer> concat = new ArrayList<>(10);

        // when
        Observable.concat(
                Observable.range(1,5),
                Observable.range(6,5)
        ).subscribe(concat::add);

        // then
        assertThat(concat.get(0)).isEqualTo(1);
        assertThat(concat.get(1)).isEqualTo(2);
        assertThat(concat.get(2)).isEqualTo(3);
        assertThat(concat.get(3)).isEqualTo(4);
        assertThat(concat.get(4)).isEqualTo(5);
        assertThat(concat.get(5)).isEqualTo(6);
        assertThat(concat.get(6)).isEqualTo(7);
        assertThat(concat.get(7)).isEqualTo(8);
        assertThat(concat.get(8)).isEqualTo(9);
        assertThat(concat.get(9)).isEqualTo(10);
    }

    @Test
    public void startsWith() {

        // given
        List<Integer> result = new ArrayList<>(11);

        // when
        Observable.range(1,10)
                .startWith(200)
                .subscribe();

        // then
        assertThat(result.get(0)).isEqualTo(200);

    }

    @Test
    public void onlyFirstWhoEmitWillEmit() throws InterruptedException {

        // given
        AtomicInteger emited = new AtomicInteger();

        Observable<Integer> first = Observable.just(10).delay(10, TimeUnit.MILLISECONDS);
        Observable<Integer> second = Observable.just(100).delay(100, TimeUnit.MILLISECONDS);
        Observable<Integer> third = Observable.just(1000).delay(1000, TimeUnit.MILLISECONDS);

        // when
        Observable.amb(Arrays.asList(first, second, third))
                .subscribe(emited::set);

        Thread.sleep(1200);

        // then
        assertThat(emited.get()).isEqualTo(10);


    }


}
