package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class Breadcrumbs extends AbstractComponent {
    public Breadcrumbs(By parentLocator) {
        super(byChained(parentLocator, By.cssSelector("[data-testid='breadcrumbs']")));
    }

    public void clickOnLink(String linkText) {
        findElementsContainingText(byChained(elementLocator, By.cssSelector("a")), linkText).get(0).click();
    }
}
