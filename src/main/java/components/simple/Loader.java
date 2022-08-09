package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class Loader extends AbstractComponent {

    public Loader(By parentLocator) {
        super(byChained(parentLocator, By.cssSelector("[data-testid='loader']")));
    }

    public void waitForDisappearance() {
        wait.until(invisibilityOfElementLocated(child.component(Loader::new).elementLocator));
    }
}
