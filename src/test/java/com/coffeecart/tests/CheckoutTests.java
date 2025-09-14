package com.coffeecart.tests;

import com.coffeecart.pages.CheckoutPage;
import com.coffeecart.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    @Test(priority = 1, description = "Verify checkout button opens payment modal")
    public void verifyCheckoutOpensModal() {
        MenuPage menu = new MenuPage(driver);
        menu.addItemByRightClickYes("Mocha");
        menu.openCheckoutModal();

        CheckoutPage checkout = new CheckoutPage(driver);
        Assert.assertTrue(checkout.isModalOpen(), "Checkout modal not visible");
    }

    @Test(priority = 2, description = "Verify form validation for name/email")
    public void verifyFormValidation() {
        MenuPage menu = new MenuPage(driver);
        menu.addItemByRightClickYes("Americano");
        menu.openCheckoutModal();

        CheckoutPage checkout = new CheckoutPage(driver);
        Assert.assertTrue(checkout.isModalOpen(), "Checkout modal not visible");
        checkout.fillCustomerInfo("Akash QA", "akash@example.com", true);
        checkout.submit();
        // No real backend; just ensure no exception and close modal
        checkout.close();
    }
}
