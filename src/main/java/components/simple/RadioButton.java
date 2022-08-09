package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

import static constants.AttributeNamesAndValues.INPUT;

public class RadioButton extends AbstractComponent {

    public RadioButton(By parentLocator, By radioButtonLocator) {
        super(byChained(parentLocator, radioButtonLocator));
    }

    public void select() {
        if (isNotSelected()) {
            scrollToElement();
            findElement(elementLocator).click();
        }
    }

    public boolean isSelected() {
        return findElement(byChained(elementLocator, By.cssSelector(INPUT))).isSelected();
    }

    public boolean isNotSelected() {
        return !isSelected();
    }
}
