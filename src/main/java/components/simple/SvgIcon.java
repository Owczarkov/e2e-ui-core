package components.simple;

import components.AbstractComponent;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

public class SvgIcon extends AbstractComponent {
    public SvgIcon(By parentLocator, By svgIconLocator) {
        super(byChained(parentLocator, svgIconLocator));
    }

    public boolean isTypeOf(String type) {
        return attributeContains("alt", type);
    }

    public void click() {
        getElement().click();
    }

    @AllArgsConstructor
    public enum IconLocator {
        X_ICON("icon-cancel"),
        MAGNIFIER_ICON("icon-search");

        public final String value;
    }
}
