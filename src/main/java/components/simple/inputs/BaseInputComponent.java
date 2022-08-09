package components.simple.inputs;

import components.AbstractComponent;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static config.DriverFactory.isMacOS;
import static constants.AttributeNamesAndValues.VALUE;

public abstract class BaseInputComponent extends AbstractComponent {
    protected BaseInputComponent(By parentLocator, By elementLocator) {
        super(byChained(parentLocator, elementLocator));
    }

    public void clearField() {
        scrollToElement(getTextInputElement());
        if (StringUtils.isNotBlank(getValue())) {
            if (isMacOS()) {
                getTextInputElement().sendKeys(Keys.COMMAND + "a" + Keys.DELETE);
            } else {
                getTextInputElement().sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
            }
        }
    }

    public WebElement getTextInputElement() {
        return findElement(elementLocator);
    }

    public String getValue() {
        return getTextInputElement().getAttribute(VALUE);
    }
}
