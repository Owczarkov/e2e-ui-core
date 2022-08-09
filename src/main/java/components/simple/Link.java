package components.simple;

import components.AbstractComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URI;
import java.util.UUID;

@Slf4j
public class Link extends AbstractComponent {
    public Link(By parentLocator, By linkLocator) {
        super(byChained(parentLocator, linkLocator));
    }

    public String getHrefValue() {
        return findElement(elementLocator).getAttribute("href");
    }

    public URI getHrefValueAsUri() {
        return URI.create(getHrefValue());
    }

    public UUID getUUIDFromHref() {
        UUID uuid = null;
        final var hrefPathTab = getHrefValueAsUri().getPath().split("/");

        for (var item : hrefPathTab) {
            try {
                uuid = UUID.fromString(item);
                break;
            } catch (IllegalArgumentException exception) {
                log.error(exception + " <-- It is not UUID");
            }
        }
        return uuid;
    }

    public String getTarget() {
        return findElement(elementLocator).getAttribute("target");
    }

    public boolean opensInNewTab() {
        return StringUtils.isNotBlank(getHrefValue()) && StringUtils.equals(getTarget(), "_blank");
    }

    public void click() {
        final var link = findElement(elementLocator);
        wait.until(ExpectedConditions.elementToBeClickable(link));
        scrollToElement().click();
    }
}
