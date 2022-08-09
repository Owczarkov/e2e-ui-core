package config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.awt.*;
import java.awt.image.BufferedImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Screenshot {

    public static BufferedImage get() {
        final var screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(DriverFactory.getDriver());
        return resize(screenshot.getImage());
    }

    public static BufferedImage resize(BufferedImage img) {
        final var scaledHeight = (int) (img.getHeight() * 0.75);
        final var scaledWidth = (int) (img.getWidth() * 0.75);
        final var tmp = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        final var resized = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        final var graphics2D = resized.createGraphics();
        graphics2D.drawImage(tmp, 0, 0, null);
        graphics2D.dispose();
        return resized;
    }
}
