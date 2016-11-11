package pl.mprzybylak.presentation.rxjavaquick;

import io.reactivex.Observable;
import org.junit.Test;
import pl.mprzybylak.presentation.rxjavaquik.Money;

import java.util.Arrays;
import java.util.List;
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

    private Money money(long value) {
        return new Money(value);
    }

}
