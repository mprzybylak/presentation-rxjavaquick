package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Observable.range(1,1000)
            .groupBy(i -> i % 2)
            .subscribe(groups -> {
                if(groups.getKey() == 0) {
                    groups.subscribe(evenNumbers::add);
                }
                else {
                    groups.subscribe(oddNumbers::add);
                }
            });

        // then
        evenNumbers.forEach(o -> assertThat(o).is(EVEN));
        oddNumbers.forEach(o -> assertThat(o).isNot(EVEN));
    }

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
