import components.simple.Label;
import config.DriverFactory;
import config.Screenshot;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ImplementationTests {

    @Test
    public void verifyIfSeleniumOpensBrowserAndNavigateToProperSite() {
        DriverFactory.setEnvUrl("https://petstore.swagger.io/");
        final var label = new Label(By.cssSelector("body"), By.cssSelector("#swagger-ui"));
        assertThat(label.isVisible()).isTrue();
    }

    @AfterMethod(alwaysRun = true)
    @SneakyThrows
    protected void teardown(ITestResult result) {
        if (DriverFactory.isDriverInitialized()) {
            if (!result.isSuccess()) {
                final var byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(Screenshot.get(), "png", byteArrayOutputStream);
                Allure.addAttachment(result.getName(), new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            }

            DriverFactory.quitDriver();
        }
    }
}
