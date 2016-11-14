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

    @Test
    public void helloWorldFluent() {

        // given
        String[] hello = new String[] {"H", "e", "l", "l", "o", ",", " ", "W", "o", "r", "l", "d", "!"};
        StringBuilder sb = new StringBuilder();

        // when
        Observable.fromArray(hello).subscribe(sb::append);

        // then
        assertThat(sb.toString()).isEqualTo("Hello, World!");
    }

    @Test
    public void simpleScenario() {

        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(i -> i % 2 == 0)
                .map(i -> i * 2)
                .take(3)
                .reduce((first, sec) -> first + sec)
                .subscribe(
                        System.out::println,   // on each element
                        System.err::println,   // on error
                        () -> System.out.println("End!")  // on finish

                );
    }

}
