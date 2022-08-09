package config;

import java.time.Duration;
import java.util.function.Supplier;

public class TimeOutHandler {

    private static final int DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 30;

    public static int getDefaultImplicitlyWaitTimeout() {
        return DEFAULT_IMPLICITLY_WAIT_TIMEOUT;
    }

    public void overrideImplicitlyWait(int milliseconds) {
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(milliseconds));
    }

    public void restoreImplicitlyWait() {
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICITLY_WAIT_TIMEOUT));
    }

    public <T> T getWithTimeout(int waitMillis, Supplier<T> elementValueSupplier) {
        overrideImplicitlyWait(waitMillis);
        final var value = elementValueSupplier.get();
        restoreImplicitlyWait();
        return value;
    }
}
