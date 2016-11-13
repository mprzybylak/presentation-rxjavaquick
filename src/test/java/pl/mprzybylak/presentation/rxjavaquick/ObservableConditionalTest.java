package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableConditionalTest {

    @Test
    public void allOf() {

        // given
        AtomicBoolean allLessThan100 = new AtomicBoolean();

        // when
        Observable.range(1, 10)
                .all(i -> i < 100)
                .subscribe(allLessThan100::set);

        // then
        assertThat(allLessThan100.get()).isTrue();
    }

    @Test
    public void doesObservableContains() {

        // given
        AtomicBoolean contain = new AtomicBoolean();

        // when
        Observable.range(1,10)
                .contains(15)
                .subscribe(contain::set);

        // then
        assertThat(contain.get()).isFalse();
    }

    @Test
    public void returnDefaultValueIfObservableIsEmpty() {

        // given
        AtomicInteger nonEmptyObservableValue = new AtomicInteger();
        AtomicInteger emptyObservableValue = new AtomicInteger();

        // when
         Observable.just(1)
                .defaultIfEmpty(100)
                .subscribe(nonEmptyObservableValue::set);

        Observable.<Integer>empty()
                .defaultIfEmpty(100)
                .subscribe(emptyObservableValue::set);

        // then
        assertThat(nonEmptyObservableValue.get()).isEqualTo(1);
        assertThat(emptyObservableValue.get()).isEqualTo(100);
    }

    @Test
    public void doesObservablesEmitsTheSameElements() {

        // given
        AtomicBoolean isEquals = new AtomicBoolean();

        // when
        Observable<Long> first = Observable.intervalRange(1, 10, 100, 10, TimeUnit.MILLISECONDS);
        Observable<Long> second = Observable.intervalRange(1, 10, 200, 25, TimeUnit.MILLISECONDS);

        Observable.sequenceEqual(first, second)
                .subscribe(isEquals::set);

        // then
        assertThat(isEquals.get()).isTrue();

    }

}
