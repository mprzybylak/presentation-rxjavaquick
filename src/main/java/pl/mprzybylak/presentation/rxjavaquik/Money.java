package pl.mprzybylak.presentation.rxjavaquik;

/**
 * Basically dummy wrapper around long value
 */
public class Money {

    private final Object lock = new Object();
    private long value;

    public Money(long initialValue) {
        value = initialValue;
    }

    public long value() {
        synchronized (lock) {
            return value;
        }
    }

    public void add(Money toAdd) {
        synchronized (lock) {
            value = value + toAdd.value();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return value == money.value;

    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
