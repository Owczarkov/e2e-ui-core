package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class Image extends AbstractComponent {
    public Image(By parentLocator, By imageLocator) {
        super(byChained(parentLocator, imageLocator));
    }
}
