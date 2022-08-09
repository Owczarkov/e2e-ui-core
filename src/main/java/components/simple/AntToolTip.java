package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

import java.util.function.Supplier;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AntToolTip extends AbstractComponent {
    private final By elementLocatorOfRelatedComponent;

    public AntToolTip(By elementLocatorOfRelatedComponent) {
        super(By.cssSelector(".ant-tooltip:not(.ant-tooltip-hidden)"));
        this.elementLocatorOfRelatedComponent = elementLocatorOfRelatedComponent;
    }

    @Override
    public String getText() {
        return getFromToolTip(super::getText);
    }

    private <T> T getFromToolTip(Supplier<T> supplier) {
        if (isPresentAndVisible()) {
            final var element = findElement(elementLocator);
            actions.moveToElement(element, -element.getLocation().x, 0).build().perform();
        }

        actions.moveToElement(findElement(elementLocatorOfRelatedComponent)).build().perform();
        wait.until(visibilityOfElementLocated(elementLocator));
        final var value = supplier.get();

        actions.moveByOffset(findElement(elementLocatorOfRelatedComponent).getSize().width, 0).build().perform();
        return value;
    }
}
