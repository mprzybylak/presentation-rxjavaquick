package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldTest {

    @Test
    public void helloWorld() {

        // given
        String[] hello = new String[] {"H", "e", "l", "l", "o", ",", " ", "W", "o", "r", "l", "d", "!"};
        StringBuilder sb = new StringBuilder();

        // when
        Observable<String> observable = Observable.fromArray(hello);
        Disposable subscribe = observable.subscribe(sb::append);

        // then
        assertThat(sb.toString()).isEqualTo("Hello, World!");
    }

}
