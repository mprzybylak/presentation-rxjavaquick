package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableTransformationsTest {

    private static final Condition<Integer> EVEN = new Condition<>(integer -> integer % 2 == 0, "cond");

    @Test
    public void simpleTransform() {

        // given
        List<Integer> input = Arrays.asList(1,2,3,4,5);
        List<Integer> output = new ArrayList<>(5);

        // when
        Observable.fromIterable(input)
                .map(i -> i * 2)
                .subscribe(output::add);

        // then
        assertThat(output.get(0)).isEqualTo(input.get(0) * 2);
        assertThat(output.get(1)).isEqualTo(input.get(1) * 2);
        assertThat(output.get(2)).isEqualTo(input.get(2) * 2);
        assertThat(output.get(3)).isEqualTo(input.get(3) * 2);
        assertThat(output.get(4)).isEqualTo(input.get(4) * 2);
    }

    @Test
    public void mapToObservableAndConcatWithOrdering() {

        // given
        List<Integer> input = Arrays.asList(1,2,3);
        List<Integer> output = new ArrayList<>(9);

        // when
        Observable.fromIterable(input)
                .concatMap(i -> Observable.range(i, 3))
                .subscribe(output::add);

        // then
        assertThat(output.get(0)).isEqualTo(1);
        assertThat(output.get(1)).isEqualTo(2);
        assertThat(output.get(2)).isEqualTo(3);

        assertThat(output.get(3)).isEqualTo(2);
        assertThat(output.get(4)).isEqualTo(3);
        assertThat(output.get(5)).isEqualTo(4);

        assertThat(output.get(6)).isEqualTo(3);
        assertThat(output.get(7)).isEqualTo(4);
        assertThat(output.get(8)).isEqualTo(5);
    }

    @Test
    public void mapToObservableAndMergeWithoutOrderGuarantee() {

        // given
        List<Integer> input = Arrays.asList(1,2,3);
        List<Integer> output = new ArrayList<>(9);

        // when
        Observable.fromIterable(input)
                .flatMap(i -> Observable.range(i, 3))
                .subscribe(output::add);

        // then
        assertThat(output).contains(1, 2, 3, 4, 5);

    }

    @Test
    public void groupBy() {

        // given
        List<Integer> oddNumbers = new ArrayList<>(500);
        List<Integer> evenNumbers = new ArrayList<>(500);

        // when
        Observable.range(1, 1000)
            .groupBy(i -> i % 2)
            .subscribe(groups -> {
                if (groups.getKey() == 0) {
                    groups.subscribe(evenNumbers::add);
                } else {
                    groups.subscribe(oddNumbers::add);
                }
            });

        // then
        evenNumbers.forEach(o -> assertThat(o).is(EVEN));
        oddNumbers.forEach(o -> assertThat(o).isNot(EVEN));
    }

    @Test
    public void accumulateItems() {

        // given
        AtomicInteger sum = new AtomicInteger(0);

        // when
        Observable.range(1,5)
                .scan((first, second) -> first + second)
                .subscribe(sum::set);

        // then
        assertThat(sum.get()).isEqualTo(1 + 2 + 3 + 4 + 5);
    }

    @Test
    public void bufferItems() {

        // given
        List<Integer> sumsOfTwoValues = new ArrayList<>(5);

        // when
        Observable.range(1, 10)
                .buffer(2)
                .subscribe(b -> sumsOfTwoValues.add(b.stream()
                        .mapToInt(Integer::intValue)
                        .sum())
                );

        // then
        assertThat(sumsOfTwoValues.get(0)).isEqualTo(1 + 2);
        assertThat(sumsOfTwoValues.get(1)).isEqualTo(3 + 4);
        assertThat(sumsOfTwoValues.get(2)).isEqualTo(5 + 6);
        assertThat(sumsOfTwoValues.get(3)).isEqualTo(7 + 8);
        assertThat(sumsOfTwoValues.get(4)).isEqualTo(9 + 10);
    }

    @Test
    public void window() {

        // given
        List<AtomicInteger> sumsOfTwoValues = atomicIntegerArrayList(5);
        Iterator<AtomicInteger> it = sumsOfTwoValues.iterator();

        // when
        Observable.range(1, 10)
                .window(2)
                .subscribe(o -> {
                    AtomicInteger sum = it.next();
                    o.subscribe(sum::addAndGet);
                });

        // then
        assertThat(sumsOfTwoValues.get(0).get()).isEqualTo(1 + 2);
        assertThat(sumsOfTwoValues.get(1).get()).isEqualTo(3 + 4);
        assertThat(sumsOfTwoValues.get(2).get()).isEqualTo(5 + 6);
        assertThat(sumsOfTwoValues.get(3).get()).isEqualTo(7 + 8);
        assertThat(sumsOfTwoValues.get(4).get()).isEqualTo(9 + 10);
    }

    private List<AtomicInteger> atomicIntegerArrayList(int size) {
        List<AtomicInteger> list = new ArrayList<>(size);
        for(int i = 0; i < size; ++i) {
            list.add(new AtomicInteger());
        }
        return list;
    };

    @Test
    public void castToDifferentType() {

        // given
        List<Object> input = Arrays.asList(1,2,3,4,5);
        List<Integer> output = new ArrayList<>(5);

        // when
        Observable.fromIterable(input)
                .cast(Integer.class).flatMap(integer -> Observable.range(integer, 5))
                .subscribe(output::add);

        // then
        assertThat(output.get(0)).isEqualTo(input.get(0));
        assertThat(output.get(1)).isEqualTo(input.get(1));
        assertThat(output.get(2)).isEqualTo(input.get(2));
        assertThat(output.get(3)).isEqualTo(input.get(3));
        assertThat(output.get(4)).isEqualTo(input.get(4));
    }




}
