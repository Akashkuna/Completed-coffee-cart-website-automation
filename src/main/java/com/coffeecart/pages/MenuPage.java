package com.coffeecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class MenuPage extends BasePage {

    private final By menuItemsTitles = By.cssSelector("ul li h4");
    private final By cupBodies       = By.cssSelector("div.cup-body");
    private final By cartLink        = By.cssSelector("a[aria-label='Cart page']");
    private final By totalButton     = By.cssSelector("button[data-test='checkout']");

    // Add-to-cart dialog opened by RIGHT-CLICK
    private final By addToCartDialog = By.cssSelector("dialog[data-cy='add-to-cart-modal']");
    private final By modalYes        = By.xpath("//dialog[@data-cy='add-to-cart-modal']//button[normalize-space()='Yes']");
    private final By modalNo         = By.xpath("//dialog[@data-cy='add-to-cart-modal']//button[normalize-space()='No']");

    // Upsell banner buttons (text can vary slightly; keep it fuzzy)
    private final By upsellYes = By.xpath("//button[contains(normalize-space(.),'Yes, of course') or contains(normalize-space(.),'Yes')]");
    private final By upsellNo  = By.xpath("//button[contains(normalize-space(.),'Nah') or contains(normalize-space(.),'skip')]");

    public MenuPage(WebDriver driver) { super(driver); }

    /** Guard: wait until menu cups are present */
    public void waitForMenuLoaded() {
        wait.until(d -> d.findElements(cupBodies).size() > 0);
    }

    public int getMenuItemsCount() {
        return driver.findElements(cupBodies).size();
    }

    public List<String> getMenuItemNames() {
        return driver.findElements(menuItemsTitles).stream()
                .map(e -> e.getText().split("\\n")[0].trim())
                .collect(Collectors.toList());
    }

    public boolean hasProduct(String name) {
        String xp = String.format("//div[@class='cup-body' and @aria-label='%s']", name);
        return !driver.findElements(By.xpath(xp)).isEmpty();
    }

    private WebElement cupByName(String name) {
        String xpath = String.format("//div[@class='cup-body' and @aria-label='%s']", name);
        return waitClickable(By.xpath(xpath));
    }

    // ==== Right-click flow ====
    public void openAddToCartDialogByRightClick(String name) {
        WebElement cup = cupByName(name);
        contextClick(cup);
        waitVisible(addToCartDialog);
    }

    public void confirmAddToCartYes() { click(modalYes); }
    public void confirmAddToCartNo()  { click(modalNo);  }

    public void addItemByRightClickYes(String name) {
        openAddToCartDialogByRightClick(name);
        confirmAddToCartYes();
        waitVisible(cartLink); // quick sync point
    }

    public boolean isAddToCartDialogVisible() {
        try {
            WebElement dlg = driver.findElement(addToCartDialog);
            return dlg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==== Left-click flow for upsell ====
    public void leftClickOnItem(String name) {
        cupByName(name).click();
    }

    public boolean isUpsellVisible() {
        try {
            return driver.findElement(upsellYes).isDisplayed() || driver.findElement(upsellNo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptUpsell() { click(upsellYes); }
    public void declineUpsell() { click(upsellNo); }

    // ==== Cart / totals ====
    public void openCartLink() { click(cartLink); }
    /** Example: "cart (2)" */
    public String getCartBadgeText() { return waitVisible(cartLink).getText(); }
    /** Example: "Total: $19.00" */
    public String getTotalText()     { return waitVisible(totalButton).getText(); }
    public void openCheckoutModal()  { click(totalButton); }
}
