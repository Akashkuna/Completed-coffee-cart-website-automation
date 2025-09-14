package com.coffeecart.tests;

import com.coffeecart.data.TestDataProvider;
import com.coffeecart.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MenuTests extends BaseTest {

    @Test(priority = 1, description = "Verify menu page loads correctly")
    public void verifyMenuLoads() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        Assert.assertTrue(menu.getMenuItemsCount() > 0, "Menu items did not load");
        log.info("Menu items: " + menu.getMenuItemNames());
    }

    @Test(priority = 2, description = "Verify coffee items are displayed (Mocha present)")
    public void verifyCoffeeItemsDisplayed() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        Assert.assertTrue(menu.hasProduct("Mocha"), "Mocha not listed");
    }

    @Test(priority = 3, description = "Verify right-click opens add-to-cart dialog")
    public void verifyRightClickOpensDialog() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        menu.openAddToCartDialogByRightClick("Espresso");
        Assert.assertTrue(menu.isAddToCartDialogVisible(), "Add-to-cart dialog not visible after right-click");
        menu.confirmAddToCartNo();
    }

    @Test(priority = 4, description = "Verify cart icon updates when adding item via right-click > Yes")
    public void verifyCartBadgeUpdates() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        String before = menu.getCartBadgeText(); // e.g., cart (0)
        menu.addItemByRightClickYes("Mocha");
        String after  = menu.getCartBadgeText();
        Assert.assertTrue(after.contains("(1)"), "Cart count did not update: " + after + " from " + before);
    }

    @Test(priority = 5, dataProvider = "coffeeItems", dataProviderClass = TestDataProvider.class,
            description = "Add multiple items using right-click Yes and verify total text updates")
    public void addMultipleItemsDDT(String itemName, String price) {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        menu.addItemByRightClickYes(itemName);
        String total = menu.getTotalText();
        Assert.assertTrue(total.startsWith("Total: $"), "Total is not visible/updated: " + total);
    }
}
