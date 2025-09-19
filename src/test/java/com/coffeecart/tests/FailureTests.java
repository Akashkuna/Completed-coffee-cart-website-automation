package com.coffeecart.tests;
import com.coffeecart.pages.CheckoutPage;
import com.coffeecart.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;
public class FailureTests extends BaseTest {
    @Test(priority = 1, description = "Intentional failure: show screenshot of checkout modal")
    public void showFailureScreenshot() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        menu.addItemByRightClickYes("Mocha");
        menu.openCheckoutModal();
        CheckoutPage checkout = new CheckoutPage(driver);
        Assert.assertTrue(checkout.isModalOpen(), "Checkout modal should be open for the demo.");
        Assert.fail("Intentional demo failure — screenshot should show the checkout modal.");
    }
    @Test(priority = 2, description = "Intentional failure: impossible cart badge expectation")
    public void wrongBadgeCount() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();
        String before = menu.getCartBadgeText();
        menu.addItemByRightClickYes("Americano");
        String after  = menu.getCartBadgeText();
        Assert.assertTrue(after.contains("(5)"),
                "Intentional bad expectation. Before=" + before + ", After=" + after);
    }
}
