package components.simple.dropdowns;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static constants.AttributeNamesAndValues.INPUT;

public class AntSelectSearch extends AbstractComponent {

    public final By resultItem = byChained(By.cssSelector(".ant-select-dropdown"), By.cssSelector(".ant-select-dropdown-menu-item"));

    public AntSelectSearch(By parentLocator, By antSelectSearchLocator) {
        super(byChained(parentLocator, antSelectSearchLocator));
    }

    public void selectByName(String name) {
        getSelectSearchElement().clear();
        getSelectSearchElement().sendKeys(name);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(resultItem, "No result available.")));
        findElementsContainingText(resultItem, name).get(0).click();
    }

    private WebElement getSelectSearchElement() {
        return findElement(byChained(elementLocator, By.cssSelector(INPUT)));
    }
}
