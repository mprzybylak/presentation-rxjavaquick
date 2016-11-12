package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        Assertions.assertThat(combined.get(0)).isEqualTo(1 + 20);
        Assertions.assertThat(combined.get(1)).isEqualTo(2 + 21);
        Assertions.assertThat(combined.get(2)).isEqualTo(3 + 22);
        Assertions.assertThat(combined.get(3)).isEqualTo(4 + 23);
        Assertions.assertThat(combined.get(4)).isEqualTo(5 + 24);
        Assertions.assertThat(combined.get(5)).isEqualTo(6 + 25);
        Assertions.assertThat(combined.get(6)).isEqualTo(7 + 26);
        Assertions.assertThat(combined.get(7)).isEqualTo(8 + 27);
        Assertions.assertThat(combined.get(8)).isEqualTo(9 + 28);
        Assertions.assertThat(combined.get(9)).isEqualTo(10 + 29);
    }

}
