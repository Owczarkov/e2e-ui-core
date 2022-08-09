package components.simple.inputs;

import components.simple.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TextArea extends BaseInputComponent {
    public final Label errorMessage = new Label(elementLocator, By.cssSelector("._input-field-error"));

    public TextArea(By parentLocator, By textFieldLocator) {
        super(parentLocator, textFieldLocator);
    }

    public String getPlaceholderText() {
        return timeouts.getWithTimeout(100, () -> getTextInputElement().getAttribute("placeholder"));
    }

    public void insertValue(String data) {
        clearField();
        getTextInputElement().sendKeys(data);
        getTextInputElement().sendKeys(Keys.TAB);
    }

    @Override
    public WebElement getTextInputElement() {
        return findElement(byChained(elementLocator, By.cssSelector("textarea")));
    }
}
