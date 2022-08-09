package components;

public interface Stateful {

    boolean isEnabled();

    boolean isVisible();

    boolean isNotVisible();

    default boolean isDisabled() {
        return !isEnabled();
    }
}
