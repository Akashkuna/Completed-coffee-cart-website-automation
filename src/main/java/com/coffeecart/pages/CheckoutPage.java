package com.coffeecart.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private final By modalRoot   = By.cssSelector(".modal .modal-content");
    private final By nameInput   = By.cssSelector("form [name='name']");
    private final By emailInput  = By.cssSelector("form [name='email']");
    private final By promoCheck  = By.cssSelector("form [name='promotion']");
    private final By submitBtn   = By.cssSelector("#submit-payment");
    private final By closeBtn    = By.cssSelector(".modal .close");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isModalOpen() {
        try {
            return waitVisible(modalRoot).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void fillCustomerInfo(String name, String email, boolean promo) {
        type(nameInput, name);
        type(emailInput, email);
        if (promo) {
            WebElement box = waitVisible(promoCheck);
            if (!box.isSelected()) {
                box.click();
            }
        }
    }

    public void submit() {
        click(submitBtn);
        // The demo may keep modal open; no hard assert here.
    }

    /** Be tolerant: try close button, else ESC, else no-op */
    public void close() {
        try {
            click(closeBtn);
        } catch (TimeoutException | NoSuchElementException e) {
            try {
                driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
            } catch (Exception ignore) {
                try {
                    ((JavascriptExecutor) driver).executeScript(
                            "document.querySelector('.modal .close')?.click();");
                } catch (Exception ignored) {}
            }
        }
    }
}
