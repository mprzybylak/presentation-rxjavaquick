package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableFilteringTest {

    private static final Condition<Integer> EVEN = new Condition<>(integer -> integer % 2 == 0, "cond");

    @Test
    public void onlyElementsMatchPredicate() {

        // given
        List<Integer> evenNumbers = new ArrayList<>(500);

        // when
        Observable.range(1, 1000)
                .filter(i -> i % 2 == 0)
                .subscribe(evenNumbers::add);

        // then
        evenNumbers.forEach(e -> assertThat(e).is(EVEN));
    }

    @Test
    public void onlyFirstThatMatchPredicate() {

        // given
        AtomicInteger firstEven = new AtomicInteger();

        // when
        Observable.range(1, 1000)
                .takeFirst(i -> i % 2 == 0);

        assertThat(firstEven.get()).is(EVEN);
    }

    @Test
    public void onlyDistinctElements() {

        // given
        List<Integer> withDupicates = Arrays.asList(1,1,2,2,3,3,4,4,5,5);
        List<Integer> withoutDuplicates = new ArrayList<>(5);

        // when
        Observable.fromIterable(withDupicates)
                .distinct()
                .subscribe(withoutDuplicates::add);

        // then
        assertThat(withoutDuplicates).hasSize(5);
    }

    @Test
    public void onlyFirstElement() {

        // given
        AtomicInteger firstValue = new AtomicInteger();

        // when
        Observable.range(1, 1000)
                .first()
                .subscribe(firstValue::set);

        // then
        assertThat(firstValue.get()).isEqualTo(1);
    }

    @Test
    public void onlyLastElement() {

        // given
        AtomicInteger firstValue = new AtomicInteger();

        // when
        Observable.range(1, 1000)
                .last()
                .subscribe(firstValue::set);

        // then
        assertThat(firstValue.get()).isEqualTo(1000);
    }

    @Test
    public void onlyNthElement() {

        // given
        List<Integer> input = Arrays.asList(1,2,3,4,5);
        AtomicInteger thirdItem = new AtomicInteger();

        // when
        Observable.fromIterable(input)
                .elementAt(2)
                .subscribe(thirdItem::set);

        // then
        assertThat(thirdItem.get()).isEqualTo(3);
    }

    @Test
    public void skipFirstNElements() {

        // given
        List<Integer> lastElements = new ArrayList<>(90);

        // when
        Observable.range(1, 100)
                .skip(10)
                .subscribe(lastElements::add);

        // then
        assertThat(lastElements.get(0)).isEqualTo(11);
    }

    @Test
    public void skipLastNElements() {

        // given
        List<Integer> firstElements = new ArrayList<>(90);

        // when
        Observable.range(1, 100)
                .skipLast(10)
                .subscribe(firstElements::add);

        // then
        assertThat(firstElements.get(89)).isEqualTo(90);
    }

    @Test
    public void takeFirstNElements() {

        // given
        List<Integer> firstElements = new ArrayList<>(10);

        // when
        Observable.range(1, 100)
                .take(10)
                .subscribe(firstElements::add);

        // then
        assertThat(firstElements.get(9)).isEqualTo(10);
    }



}
