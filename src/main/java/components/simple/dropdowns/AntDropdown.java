package components.simple.dropdowns;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class AntDropdown extends AbstractComponent {
    private final By listCollapsed = byChained(elementLocator, By.cssSelector(".ant-dropdown-trigger"));
    private final By listExpanded = byChained(elementLocator, By.cssSelector(".ant-dropdown-open"));

    public AntDropdown(By parentLocator, By dropdownLocator) {
        super(byChained(parentLocator, dropdownLocator));
    }

    public void selectByName(String name) {
        scrollToElement();
        clickDropdown();
        findElementsContainingText(By.cssSelector(".ant-dropdown-menu-item"), name).get(0).click();
    }

    public void clickDropdown() {
        if (findElement(listCollapsed).isDisplayed()) {
            findElement(listCollapsed).click();
        } else {
            findElement(listExpanded).click();
        }
    }
}
