package components;

import config.TimeOutHandler;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static components.AbstractComponent.byChained;

@AllArgsConstructor
public class ChildComponentFactory {

    private final AbstractComponent parent;

    public <T extends AbstractComponent> T component(Function<By, T> childConstructor) {
        return create(childConstructor);
    }

    public <T extends AbstractComponent> T component(By locator, BiFunction<By, By, T> childConstructor) {
        return create(locator, childConstructor);
    }

    public <T extends AbstractComponent, P> T component(By locator, P parameter, TriFunction<By, By, P, T> childConstructor) {
        return create(locator, parameter, childConstructor);
    }

    public <T extends AbstractComponent> List<T> listOf(String cssSelector, BiFunction<By, By, T> childConstructor) {
        return listOf(cssSelector, childConstructor, 1);
    }

    public <T extends AbstractComponent> List<T> listOf(String cssSelector, BiFunction<By, By, T> childConstructor, int startingIndex) {
        final var children = new TimeOutHandler().getWithTimeout(1000, () -> parent.findElements(byChained(parent.elementLocator, By.cssSelector(cssSelector))));
        return IntStream.range(startingIndex, children.size() + 1)
                .mapToObj(i -> create(By.cssSelector(cssSelector + ":nth-child(" + i + ")"), childConstructor))
                .collect(Collectors.toList());
    }

    private <T extends AbstractComponent, P> T create(By childLocator, P constructorParameter, TriFunction<By, By, P, T> childConstructor) {
        return childConstructor.apply(parent.elementLocator, childLocator, constructorParameter);
    }

    private <T extends AbstractComponent> T create(By childLocator, BiFunction<By, By, T> childConstructor) {
        return childConstructor.apply(parent.elementLocator, childLocator);
    }

    private <T extends AbstractComponent> T create(Function<By, T> childConstructor) {
        return childConstructor.apply(parent.elementLocator);
    }
}
