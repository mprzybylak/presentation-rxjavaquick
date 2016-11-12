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

}
