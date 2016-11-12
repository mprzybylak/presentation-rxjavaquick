package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


}
