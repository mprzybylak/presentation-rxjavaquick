package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.ArrayWrapperList;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableUtils {

    @Test
    public void delay() throws InterruptedException {

        // given
        List<LocalDateTime> dates = new ArrayList<>(10);
        LocalDateTime beforeSubscription = null;

        // when
        Observable<Integer> o = Observable.range(1, 10).delay(1, TimeUnit.SECONDS);
        beforeSubscription = LocalDateTime.now();
        o.subscribe(integer -> dates.add(LocalDateTime.now()));

        Thread.sleep(1100);

        // then
        int between = (int) beforeSubscription.until(dates.get(0), ChronoUnit.SECONDS);
        assertThat(between).isBetween(1, 2);
    }

    @Test
    public void actionOnFinish() {

        // given
        AtomicBoolean b = new AtomicBoolean(false);

        // when
        Observable.range(1, 10)
                .doOnComplete(() -> b.set(true))
                .subscribe();

        // then
        assertThat(b.get()).isTrue();
    }

    @Test
    public void actionOnEachElement() {} {

        // given
        List<AtomicBoolean> booleans = new ArrayList<>();

        // when
        Observable.range(1, 10)
                .doOnEach(i -> booleans.add(new AtomicBoolean(true)))
                .subscribe();

        // then
        booleans.forEach(b -> assertThat(b.get()).isTrue());
    }

}
