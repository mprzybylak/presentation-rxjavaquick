package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.junit.Test;
import pl.mprzybylak.presentation.rxjavaquik.FibonacciNumberService;
import pl.mprzybylak.presentation.rxjavaquik.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Test
    public void fromArray() {

        //given
        Money[] expensesForToday = { money(100), money(5), money(25), money(5), money(35) };
        Money totalExpenses = money(0);

        // when
        Observable.fromArray(expensesForToday).subscribe(totalExpenses::add);

        // then
        assertThat(totalExpenses).isEqualTo(money(170));
    }

    @Test
    public void fromIterable() {

        // given
        List<Money> expensesForToday = Arrays.asList(money(100), money(5), money(25), money(5), money(35));
        Money totalExpenses = money(0);

        // when
        Observable.fromIterable(expensesForToday).subscribe(totalExpenses::add);

        // then
        assertThat(totalExpenses).isEqualTo(money(170));
    }

    @Test
    public void lazyMethodCall() {

        // given
        final AtomicInteger counter = new AtomicInteger(0);
        Callable<Integer> tick = counter::incrementAndGet;

        AtomicInteger firstTick = new AtomicInteger(0);
        AtomicInteger secondTick = new AtomicInteger(0);
        AtomicInteger thirdTick = new AtomicInteger(0);

        // when
        Observable<Integer> observable = Observable.fromCallable(tick);

        observable.subscribe(firstTick::set);
        observable.subscribe(secondTick::set);
        observable.subscribe(thirdTick::set);

        // then
        assertThat(firstTick.get()).isEqualTo(1);
        assertThat(secondTick.get()).isEqualTo(2);
        assertThat(thirdTick.get()).isEqualTo(3);
    }

    @Test
    public void fromFuture() {

        // given
        Future<Integer> fibonacciComputation = new FibonacciNumberService().countNthFibonacciNumber(20);
        AtomicInteger fibonacciNumber = new AtomicInteger(0);

        // when
        Observable.fromFuture(fibonacciComputation).subscribe(fibonacciNumber::set);

        // then
        assertThat(fibonacciNumber.get()).isEqualTo(6765);
    }

    @Test
    public void orderedSequenceOfConsecutiveNumbers() {

        // given
        List<Integer> sequence = new ArrayList<>(10);

        // when
        Observable.range(0, 10).subscribe(sequence::add);

        // then
        assertThat(sequence.size()).isEqualTo(10);
        assertThat(sequence.get(0)).isEqualTo(0);
        assertThat(sequence.get(sequence.size() - 1)).isEqualTo(9);
    }


    @Test
    public void defer() {

        // given
        AtomicInteger firstResult = new AtomicInteger(0);
        AtomicInteger secondResult = new AtomicInteger(0);
        AtomicInteger thirdResult = new AtomicInteger(0);

        AtomicInteger value = new AtomicInteger(1);
        Observable<Integer> deferredObservable = Observable.defer(() -> Observable.just(value.get()));

        // when
        value.set(10);
        deferredObservable.subscribe(firstResult::set);

        value.set(100);
        deferredObservable.subscribe(secondResult::set);

        value.set(1000);
        deferredObservable.subscribe(thirdResult::set);

        // then
        assertThat(firstResult.get()).isEqualTo(10);
        assertThat(secondResult.get()).isEqualTo(100);
        assertThat(thirdResult.get()).isEqualTo(1000);
    }

    private Money money(long value) {
        return new Money(value);
    }

}
