package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Button extends AbstractComponent {
    public final AntToolTip antToolTip = child.component(AntToolTip::new);

    public Button(By parentLocator, By buttonLocator) {
        super(byChained(parentLocator, buttonLocator));
    }

    public static boolean textNotEmpty(Button button) {
        return !button.getElement().getText().isEmpty();
    }

    public void click() {
        wait.until(ExpectedConditions.elementToBeClickable(getElement()));
        scrollToElement().click();
    }
}
