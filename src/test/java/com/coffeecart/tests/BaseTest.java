package com.coffeecart.tests;
import com.coffeecart.managers.DriverManager;
import com.coffeecart.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
@Listeners({com.coffeecart.listeners.TestListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected final Logger log = LogManager.getLogger(this.getClass());
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {
        driver = DriverManager.getDriver();
        String url = ConfigReader.getProperty("url");
        log.info("Launching: " + url);
        driver.get(url);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
