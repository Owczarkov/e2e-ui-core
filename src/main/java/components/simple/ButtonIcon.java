package components.simple;

import org.openqa.selenium.By;

import static constants.AttributeNamesAndValues.ARIA_EXPANDED;

public class ButtonIcon extends Button {
    public final SvgIcon icon = required(child.component(By.cssSelector("svg"), SvgIcon::new));

    public ButtonIcon(By parentLocator, By buttonLocator) {
        super(parentLocator, buttonLocator);
    }

    public boolean isExpanded() {
        return attributeContains(ARIA_EXPANDED, "true");
    }

    public boolean isDefaultSetupCorrect() {
        return !isExpanded() && isEnabled();
    }
}
