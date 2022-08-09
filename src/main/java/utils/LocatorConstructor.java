package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocatorConstructor {

    public static By dataTestId(String dataTestIdValue) {
        return css("[data-testid='" + dataTestIdValue + "']");
    }

    public static By dataTestIdWith(String partialValue) {
        return css("[data-testid*='" + partialValue + "']");
    }

    public static By css(String cssLocator) {
        return By.cssSelector(cssLocator);
    }
}
