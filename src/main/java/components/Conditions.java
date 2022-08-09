package components;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Conditions {

    public static ExpectedCondition<Boolean> scrolledToElement(WebElement element) {
        return driver -> {
            try {
                Optional.ofNullable(driver)
                        .map(Actions::new)
                        .ifPresent(actions -> actions.moveToElement(element).build().perform());
                return true;
            } catch (MoveTargetOutOfBoundsException moveTargetOutOfBoundsException) {
                return false;
            }
        };
    }

    public static ExpectedCondition<Boolean> fulfilmentOfCondition(Supplier<?> booleanConditionAsSupplier) {
        return new ExpectedCondition<>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return booleanConditionAsSupplier.get() == Boolean.TRUE;
            }

            @Override
            public String toString() {
                return "condition to be fulfilled";
            }
        };
    }

    public static ExpectedCondition<Boolean> componentDoesHaveText(AbstractComponent component, String expectedText) {
        return new ExpectedCondition<>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return component.getText().equals(expectedText);
            }

            @Override
            public String toString() {
                return String.format("component doesnt have text: \"%s\"", expectedText);
            }
        };
    }

    public static ExpectedCondition<Boolean> hasRequiredSize(List<?> components, int requiredQuantity) {
        return new ExpectedCondition<>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return components.size() == requiredQuantity;
            }

            @Override
            public String toString() {
                return String.format("list to have \"%s\" instead of \"%s\" elements", requiredQuantity, components.size());
            }
        };
    }

    public static ExpectedCondition<Boolean> componentDoesntHaveClass(AbstractComponent component, String clazzName) {
        return new ExpectedCondition<>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return !component.hasClass(clazzName);
            }

            @Override
            public String toString() {
                return String.format("component doesnt have class: \"%s\"", clazzName);
            }
        };
    }
}
