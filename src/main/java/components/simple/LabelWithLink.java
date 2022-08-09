package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class LabelWithLink extends AbstractComponent {
    public final Link link = child.component(By.cssSelector("a"), Link::new);

    public LabelWithLink(By parentLocator, By labelLocator) {
        super(byChained(parentLocator, labelLocator));
    }
}
