package components;

import components.simple.Link;
import config.DriverFactory;
import config.TimeOutHandler;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.AttributeNamesAndValues.CLASS;
import static constants.AttributeNamesAndValues.INNER_TEXT;

@Slf4j
public abstract class AbstractComponent implements Stateful {
    public final By elementLocator;
    public final ChildComponentFactory child = new ChildComponentFactory(this);
    protected final TimeOutHandler timeouts = new TimeOutHandler();
    private final List<AbstractComponent> requiredComponents = new ArrayList<>();
    protected Wait<WebDriver> wait;
    protected WebDriver driver;
    protected Actions actions;

    protected AbstractComponent(By elementLocator) {
        super();
        DriverFactory.setDriver();
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TimeOutHandler.getDefaultImplicitlyWaitTimeout()));
        actions = new Actions(driver);
        this.elementLocator = elementLocator;
    }

    public WebElement getElement() {
        return findElement(elementLocator);
    }

    public WebElement getChild(By childLocator) {
        return findElement(byChained(elementLocator, childLocator));
    }

    public String getText() {
        return getElement().getText();
    }

    public String getAttribute(String attributeName) {
        return getElement().getAttribute(attributeName);
    }

    public List<Link> getLinks() {
        return child.listOf("a", Link::new);
    }

    public String getCssValue(String propertyName) {
        return getElement().getCssValue(propertyName);
    }

    public String getCssValue(By childLocator, String propertyName) {
        return findElement(byChained(elementLocator, childLocator)).getCssValue(propertyName);
    }

    @Override
    public boolean isEnabled() {
        if (getElement().isEnabled()) {
            return true;
        } else {
            log.error("Element is not ENABLED: {}", elementLocator);
            return false;
        }
    }

    @Override
    public boolean isVisible() {
        final var elements = findElements(elementLocator);
        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
            return true;
        } else {
            log.error("Element is not visible: {}", elementLocator);
            return false;
        }
    }

    @Override
    public boolean isNotVisible() {
        if (isNotPresent() || !isVisible()) {
            return true;
        } else {
            log.error("Element is visible, while it shouldn't be: {}", elementLocator);
            return false;
        }
    }

    public boolean isPresentAndVisible() {
        return isPresent() && isVisible();
    }

    public boolean isPresent() {
        return timeouts.getWithTimeout(100, () -> CollectionUtils.isNotEmpty(findElements(elementLocator)));
    }

    public boolean isNotPresent() {
        return timeouts.getWithTimeout(100, () -> CollectionUtils.isEmpty(findElements(elementLocator)));
    }

    public List<WebElement> findElementsContainingText(By cssSelector, String text) {
        return findElements(cssSelector)
                .stream()
                .filter(element -> element.getAttribute(INNER_TEXT).contains(text))
                .collect(Collectors.toList());
    }

    public boolean hasClass(String className) {
        return hasClass(getElement(), className);
    }

    public boolean hasClass(WebElement element, String className) {
        final var classes = timeouts.getWithTimeout(100, () -> element.getAttribute(CLASS));
        return classes.contains(className);
    }

    public boolean attributeContains(String attribute, String attributeValue) {
        return attributeContains(getElement(), attribute, attributeValue);
    }

    public boolean attributeContains(WebElement element, String attribute, String attributeValue) {
        final var dataTestIdValues = timeouts.getWithTimeout(100, () -> element.getAttribute(attribute));
        return dataTestIdValues.contains(attributeValue);
    }

    public boolean areAllComponentsVisible() {
        final var isComponentVisible = isVisible();
        if (!isComponentVisible) {
            Allure.addAttachment(String.format("%s's NOT visible", getClass().getSimpleName()), String.valueOf(elementLocator));
        }

        return isComponentVisible && requiredComponents.stream().allMatch(AbstractComponent::areAllComponentsVisible);
    }

    public void removeFromRequired(AbstractComponent component) {
        requiredComponents.remove(component);
    }

    public List<AbstractComponent> getRequiredComponents() {
        return requiredComponents;
    }

    protected WebElement scrollToElement() {
        return scrollToElement(getElement());
    }

    protected WebElement scrollToElement(WebElement element) {
        DriverFactory.getJsExecutor().executeScript("arguments[0].scrollIntoView();", element);
        actions.moveToElement(element).build().perform();
        return element;
    }

    protected Point getElementMiddlePoint() {
        return getElementMiddlePoint(getElement());
    }

    protected Point getElementMiddlePoint(WebElement element) {
        final var x = Math.addExact(element.getLocation().getX(), element.getSize().getWidth() / 2);
        final var y = Math.addExact(element.getLocation().getY(), element.getSize().getHeight() / 2);

        return new Point(x, y);
    }

    protected static ByChained byChained(By... bys) {
        return new ByChained(bys);
    }

    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    protected WebElement findElement(By by) {
        return driver.findElement(by);
    }

    protected void pressEscape() {
        findElement(By.cssSelector("html")).sendKeys(Keys.ESCAPE);
    }

    protected <T extends AbstractComponent> T required(T component) {
        requiredComponents.add(component);
        return component;
    }

    protected <T extends AbstractComponent> List<T> required(List<T> components) {
        requiredComponents.addAll(components);
        return components;
    }

    protected void required(AbstractComponent... components) {
        requiredComponents.addAll(Arrays.stream(components).collect(Collectors.toList()));
    }
}
