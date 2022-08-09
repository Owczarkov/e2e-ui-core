package components.simple.checkboxes;

import components.AbstractComponent;
import components.simple.SvgIcon;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static constants.AttributeNamesAndValues.*;

public class Checkbox extends AbstractComponent {
    private final SvgIcon icon = child.component(By.cssSelector("svg"), SvgIcon::new);

    public Checkbox(By parentLocator, By checkboxLocator) {
        super(byChained(parentLocator, checkboxLocator));
    }

    public void select() {
        if (isNotSelected()) {
            scrollToElement().click();
        }
    }

    public void unselect() {
        if (isSelected()) {
            findElement(elementLocator).click();
        }
    }

    public boolean isSelected() {
        return attributeContains(getInput(), ARIA_CHECKED, "true") &&
                icon.getAttribute(DATA_TEST_ID).contains(Icon.CHECK.value);
    }

    public boolean isPartiallySelected() {
        return attributeContains(getInput(), ARIA_CHECKED, "mixed") &&
                icon.getAttribute(DATA_TEST_ID).contains(Icon.MINUS.value);
    }

    public boolean isNotSelected() {
        return attributeContains(getInput(), ARIA_CHECKED, "false") &&
                icon.isNotPresent();
    }

    private WebElement getInput() {
        return getElement().findElement(By.cssSelector(INPUT));
    }

    @AllArgsConstructor
    public enum Icon {
        CHECK("icon-check"),
        MINUS("icon-minus");

        public final String value;
    }
}
