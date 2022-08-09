package utils;

import org.testng.SkipException;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Predicate;

public class ListOperator<T> {
    private final List<T> list;
    private final SecureRandom random = new SecureRandom();

    private ListOperator(List<T> list) {
        this.list = list;
    }

    public static <V> ListOperator<V> doOnList(List<V> list) {
        return new ListOperator<>(list);
    }

    public T getFirst() {
        return getFirst(noPredicate -> true);
    }

    public T getFirst(Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst().orElseThrow(() -> new SkipException("There are no items on list(size: " + list.size() + ") with given predicate"));
    }

    public T getRandomOrSkipWithMessage(String errorIfNotFound) {
        skipWithMessageIfEmptyList(errorIfNotFound);
        return getRandom();
    }

    private T getRandom() {
        return list.get(random.nextInt(list.size()));
    }

    private void skipWithMessageIfEmptyList(String errorIfNotFound) {
        if (list.isEmpty()) {
            throw new SkipException(errorIfNotFound);
        }
    }
}
