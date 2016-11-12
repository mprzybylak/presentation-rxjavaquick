package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

}
