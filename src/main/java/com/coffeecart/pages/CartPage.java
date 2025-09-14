package com.coffeecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By pageRoot = By.cssSelector("body");
    private final By listItems = By.cssSelector("ul li h4"); // basic check on cart page as well
    private final By totalButton = By.cssSelector("button[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains("/cart");
    }

    public List<String> getListedItems() {
        return driver.findElements(listItems).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getTotalText() {
        return waitVisible(totalButton).getText();
    }
}
