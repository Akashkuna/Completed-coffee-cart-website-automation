package com.coffeecart.managers;
import com.coffeecart.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", ConfigReader.getProperty("browser")).toLowerCase();
            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions ff = new FirefoxOptions();
                    ff.addArguments("--width=1366", "--height=768");
                    driver.set(new FirefoxDriver(ff));
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions co = new ChromeOptions();
                    co.addArguments("--remote-allow-origins=*", "--start-maximized");
                    driver.set(new ChromeDriver(co));
                    break;
            }
        }
        return driver.get();
    }
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
