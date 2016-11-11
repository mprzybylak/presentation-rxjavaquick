package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObservableTransformationsTest {

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
        Assertions.assertThat(output.get(0)).isEqualTo(input.get(0) * 2);
        Assertions.assertThat(output.get(1)).isEqualTo(input.get(1) * 2);
        Assertions.assertThat(output.get(2)).isEqualTo(input.get(2) * 2);
        Assertions.assertThat(output.get(3)).isEqualTo(input.get(3) * 2);
        Assertions.assertThat(output.get(4)).isEqualTo(input.get(4) * 2);
    }

    @Test
    public void castToDifferentType() {

        // given
        List<Object> input = Arrays.asList(1,2,3,4,5);
        List<Integer> output = new ArrayList<>(5);

        // when
        Observable.fromIterable(input)
                .cast(Integer.class)
                .subscribe(output::add);

        // then
        Assertions.assertThat(output.get(0)).isEqualTo(input.get(0));
        Assertions.assertThat(output.get(1)).isEqualTo(input.get(1));
        Assertions.assertThat(output.get(2)).isEqualTo(input.get(2));
        Assertions.assertThat(output.get(3)).isEqualTo(input.get(3));
        Assertions.assertThat(output.get(4)).isEqualTo(input.get(4));
    }


}
