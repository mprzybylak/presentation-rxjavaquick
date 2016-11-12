package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableFilteringTest {

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

}
