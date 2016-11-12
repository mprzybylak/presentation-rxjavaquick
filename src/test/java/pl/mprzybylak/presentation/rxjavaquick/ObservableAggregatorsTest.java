package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableAggregatorsTest {

    @Test
    public void concatenationOfTwoObservable() {

        // given
        List<Integer> allItems = new ArrayList<>(20);

        // when
        Observable.range(1,10)
                .concatWith(Observable.range(11,10))
                .subscribe(allItems::add);

        // then
        assertThat(allItems).hasSize(20);
        assertThat(allItems.get(0)).isEqualTo(1);
        assertThat(allItems.get(19)).isEqualTo(20);
    }

    @Test
    public void countHowManyItemsAreInObservable() {

        // given
        AtomicLong count = new AtomicLong();

        // when
        Observable.range(1,10)
                .count()
                .subscribe(count::set);

        // then
        assertThat(count.get()).isEqualTo(10);
    }

    @Test
    public void collect() {

        // given
        AtomicInteger sum = new AtomicInteger();

        // when
        Observable.range(1,100)
                .collect(AtomicInteger::new, AtomicInteger::addAndGet)
                .subscribe(i -> sum.set(i.get()));

        // then
        assertThat(sum.get()).isEqualTo(5050);
    }


}
