package components.simple.inputs;

import components.simple.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static constants.AttributeNamesAndValues.*;

public class TextField extends BaseInputComponent {
    public final Label errorMessage = child.component(By.cssSelector("._input-field-error"), Label::new);

    public TextField(By parentLocator, By textFieldLocator) {
        super(parentLocator, textFieldLocator);
    }

    public String getPlaceholderText() {
        return getTextInputElement().getAttribute(PLACEHOLDER);
    }

    public void insertValue(CharSequence... data) {
        clearField();
        getTextInputElement().sendKeys(data);
        getTextInputElement().sendKeys(Keys.TAB);
        checkIfValid();
    }

    public void insertValueAndPressEnter(CharSequence... data) {
        clearField();
        getTextInputElement().sendKeys(data);
        getTextInputElement().sendKeys(Keys.ENTER);
    }

    @Override
    public WebElement getTextInputElement() {
        return findElement(byChained(elementLocator, By.cssSelector(INPUT)));
    }

    private void checkIfValid() {
        if (hasClass(findElement(elementLocator), "-loading")) {
            wait.until(ExpectedConditions.attributeContains(elementLocator, CLASS, "-valid"));
        }
    }

    public void click() {
        scrollToElement();
        findElement(elementLocator).click();
    }
}
