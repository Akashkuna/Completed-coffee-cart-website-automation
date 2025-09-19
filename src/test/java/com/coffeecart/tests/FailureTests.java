package com.coffeecart.tests;

import com.coffeecart.pages.CheckoutPage;
import com.coffeecart.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FailureTests extends BaseTest {

    /**
     * Purposefully fails after opening the checkout modal so the screenshot
     * clearly shows a meaningful UI state (the payment dialog).
     */
    @Test(priority = 1, description = "Intentional failure: show screenshot of checkout modal")
    public void showFailureScreenshot() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();

        // Put some UI on screen for a nice-looking screenshot
        menu.addItemByRightClickYes("Mocha");
        menu.openCheckoutModal();

        CheckoutPage checkout = new CheckoutPage(driver);
        Assert.assertTrue(checkout.isModalOpen(), "Checkout modal should be open for the demo.");

        // Hard fail on purpose (Listener will capture a screenshot)
        Assert.fail("Intentional demo failure — screenshot should show the checkout modal.");
    }

    /**
     * Another guaranteed failure: asserts an impossible cart count.
     * Good backup if the modal UI changes.
     */
    @Test(priority = 2, description = "Intentional failure: impossible cart badge expectation")
    public void wrongBadgeCount() {
        MenuPage menu = new MenuPage(driver);
        menu.waitForMenuLoaded();

        String before = menu.getCartBadgeText(); // e.g., "cart (0)"
        menu.addItemByRightClickYes("Americano");
        String after  = menu.getCartBadgeText(); // e.g., "cart (1)"

        // Deliberately wrong expectation to force a fail + screenshot
        Assert.assertTrue(after.contains("(5)"),
                "Intentional bad expectation. Before=" + before + ", After=" + after);
    }
}
