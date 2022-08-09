package components.simple.inputs;

import org.openqa.selenium.By;

public class InputText extends BaseInputComponent {

    public InputText(By parentLocator, By elementLocator) {
        super(parentLocator, elementLocator);
    }

    public void overwrite(String string) {
        clearField();
        getTextInputElement().sendKeys(string);
    }
}
