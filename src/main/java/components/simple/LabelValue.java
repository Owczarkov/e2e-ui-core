package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class LabelValue extends AbstractComponent {
    public final Label label = required(child.component(By.cssSelector("._label"), Label::new));
    public final Label value = required(child.component(By.cssSelector("._value"), Label::new));

    public LabelValue(By parentLocator, By elementLocator) {
        super(byChained(parentLocator, elementLocator));
    }
}
