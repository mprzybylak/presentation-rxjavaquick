package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateObservableTest {

    @Test
    public void fromSingleObject() {

        // given
        String[] hello = new String[] {"f", "o", "o"};
        AtomicReference<String[]> result = new AtomicReference<>();

        // when
        Observable.just(hello).subscribe(result::set);

        // then
        assertThat(result.get()).isNotInstanceOf(String.class);
        assertThat(result.get()).isInstanceOf(String[].class);
    }

}
