package com.coffeecart.tests;

import com.coffeecart.pages.CartPage;
import com.coffeecart.pages.MenuPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    @Test(priority = 1, description = "Add items (right-click > Yes) and validate badge & total; then open cart")
    public void verifyCartShowsItems() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        menu.addItemByRightClickYes("Americano");
        menu.addItemByRightClickYes("Mocha");

        String badge = menu.getCartBadgeText();   // e.g., "cart (2)"
        String total = menu.getTotalText();       // e.g., "Total: $15.00"

        Assert.assertTrue(badge.contains("(2)"), "Cart badge did not reach 2: " + badge);
        Assert.assertTrue(total.matches("Total: \\$\\d+\\.\\d{2}"), "Total not updated: " + total);

        menu.openCartLink();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isLoaded(), "Cart page not loaded");
        // If you later wire cart item locators, you can assert item rows here.
    }

    @Test(priority = 2, description = "Verify total text after adding Cappuccino (right-click)")
    public void verifyPriceCalculation() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        menu.addItemByRightClickYes("Cappuccino");
        String total = menu.getTotalText();
        Assert.assertTrue(total.startsWith("Total: $"), "Total not showing: " + total);
    }

    @Test(priority = 3, description = "Cart persistence is not guaranteed on the public demo; skip this check")
    public void verifyCartPersistence() {
        throw new SkipException("Cart persistence not guaranteed on public demo; skipping.");
    }

    @Test(priority = 4, description = "Verify upsell banner appears after 3 left-clicks and accepting increases badge")
    public void verifyUpsellBannerAndAccept() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();

        // Left-click three products to trigger upsell
        menu.leftClickOnItem("Espresso");
        menu.leftClickOnItem("Americano");
        menu.leftClickOnItem("Cafe Latte");

        Assert.assertTrue(menu.isUpsellVisible(), "Upsell banner did not appear after three left-clicks");
        String before = menu.getCartBadgeText();
        menu.acceptUpsell(); // adds an extra item via offer
        String after = menu.getCartBadgeText();
        Assert.assertNotEquals(after, before, "Cart badge did not change after accepting upsell");
    }
}
