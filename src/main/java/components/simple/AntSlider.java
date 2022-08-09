package components.simple;

import components.AbstractComponent;
import org.openqa.selenium.By;

public class AntSlider extends AbstractComponent {
    private final Step step = child.component(Step::new);

    public AntSlider(By parentLocator, By sliderLocator) {
        super(byChained(parentLocator, sliderLocator));
    }

    public void select(String value) {
        final var firstPointX = step.first.getElement().getLocation().getX();
        final var lastPointX = step.last.getElement().getLocation().getX();
        final var unitX = (lastPointX - firstPointX) / 19;

        actions.moveToElement(step.first.getElement(), unitX * (Integer.parseInt(value) - 1), 0).click().build().perform();
    }

    private static class Step extends AbstractComponent {
        public final Button first = child.component(By.cssSelector(".ant-slider-dot:nth-of-type(1)"), Button::new);
        public final Button last = child.component(By.cssSelector(".ant-slider-dot:nth-of-type(5)"), Button::new);

        private Step(By parentLocator) {
            super(byChained(parentLocator, By.cssSelector(".ant-slider-step")));
        }
    }
}
