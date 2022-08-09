package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverFactory {
    public static final String ENV_URL = "envURL";
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final List<WebDriver> storedDrivers = new ArrayList<>();
    @Setter
    private static WebDriver webDriver;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> storedDrivers.forEach(WebDriver::quit)));
    }

    public static boolean isDriverInitialized() {
        return nonNull(driver.get());
    }

    public static WebDriver getDriver() {
        if (isNull(driver.get())) {
            throw new NullPointerException("Webdriver has not been setup, please use method setDriver() first.");
        }
        return driver.get();
    }

    public static void setDriver() {
        setDriver(getEnvUrl());
    }

    public static void setDriver(String url) {
        /* Browser support based on AppDynamics usage on prod env */
        final var driverType = getDriverType();
        if (isNull(driver.get())) {
            WebDriverManager.getInstance(driverType).setup();
            switch (driverType) {
                case CHROME:
                    final var chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("download.default_directory", System.getProperty("user.dir") + "/target");
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                    final var options = new ChromeOptions();
                    options.setExperimentalOption("prefs", chromePrefs);
                    options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
                    options.addArguments("--disable-cache", "--log-level=2", "--window-size=1920,1080");
                    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    transferChromeOptionsFromSystemProperties(options);
                    setChromeDriver(options);
                    break;
                case FIREFOX:
                    setWebDriver(new FirefoxDriver());
                    break;
                case EDGE:
                    setWebDriver(new EdgeDriver());
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("Driver %s not supported", driverType));
            }
            storedDrivers.add(webDriver);
            DriverFactory.driver.set(webDriver);
            setDriverProperties(url);
        }
    }

    public static JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) driver.get();
    }

    public static void quitDriver() {
        storedDrivers.remove(getDriver());
        getDriver().quit();
        driver.remove();
    }

    public static void navigateTo(String url) {
        getDriver().navigate().to(url);
    }

    public static boolean isMacOS() {
        return getOS().contains("Mac");
    }

    public static String getEnvUrl() {
        final var url = System.getProperty(ENV_URL);
        if (isNull(url) || url.isEmpty()) {
            throw new MissingFormatArgumentException("System property 'envURL' is not provided");
        }
        return url;
    }

    public static void setEnvUrl(String url) {
        System.setProperty(ENV_URL, url);
    }

    private static void setDriverProperties(String url) {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(TimeOutHandler.getDefaultImplicitlyWaitTimeout()));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(TimeOutHandler.getDefaultImplicitlyWaitTimeout()));
        getDriver().manage().deleteAllCookies();
        getDriver().get(url);
    }

    private static void transferChromeOptionsFromSystemProperties(ChromeOptions currentChromeOptions) {
        Optional.ofNullable(System.getProperty("chrome.options"))
                .ifPresent(args -> currentChromeOptions.addArguments(Arrays.asList(parseCSVHandlingQuotes(args))));
    }

    private static String[] parseCSVHandlingQuotes(String csvString) {
        return csvString.trim().split(",");
    }

    @SneakyThrows
    private static void setChromeDriver(ChromeOptions options) {
        final var gridUrl = System.getProperty("gridAddress");
        if (isNull(gridUrl)) {
            log.trace("Chrome started with below options: {}", options.toJson());
            setWebDriver(new ChromeDriver(options));
        } else {
            log.trace("Chrome remote grid started with below options: {}", options.toJson());
            log.trace("Grid address: {}", gridUrl);
            setWebDriver(new RemoteWebDriver(new URL("http://" + gridUrl + ":4444/wd/hub"), options));
        }
    }

    private static DriverManagerType getDriverType() {
        final var browser = System.getProperty("browser");
        return isNull(browser) ? DriverManagerType.CHROME : DriverManagerType.valueOf(browser);
    }

    private static String getOS() {
        return System.getProperty("os.name");
    }
}
