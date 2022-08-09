package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class Collapse extends AbstractComponent {
    private final ButtonIcon collapseButton = required(child.component(By.cssSelector("[data-testid='button']"), ButtonIcon::new));

    public Collapse(By parentLocator, By buttonLocator) {
        super(byChained(parentLocator, buttonLocator));
    }

    public boolean isExpanded() {
        return collapseButton.isExpanded();
    }

    public void toggleCollapse() {
        collapseButton.click();
    }
}
