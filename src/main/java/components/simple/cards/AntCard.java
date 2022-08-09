package components.simple.cards;

import components.AbstractComponent;
import components.simple.Label;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

import java.util.function.Predicate;

import static utils.LocatorConstructor.css;

@Slf4j
public class AntCard extends AbstractComponent implements Card {
    public final Label name = required(child.component(css(".card-name"), Label::new));
    public final Label description = child.component(css(".card-desc"), Label::new);

    public AntCard(By parentLocator, By elementLocator) {
        super(byChained(parentLocator, elementLocator));
    }

    public static Predicate<AntCard> byName(String expectedName) {
        log.info("Looking for AntCard with name {}", expectedName);
        return antCard -> antCard.name.getText().equals(expectedName);
    }

    @Override
    public void click() {
        scrollToElement().click();
    }
}
