package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class Label extends AbstractComponent {
    public Label(By parentLocator, By labelLocator) {
        super(byChained(parentLocator, labelLocator));
    }
}
