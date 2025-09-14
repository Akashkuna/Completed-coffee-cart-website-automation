package com.coffeecart.data;

import com.coffeecart.utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "coffeeItems")
    public Object[][] coffeeItems() {
        return new ExcelReader().getTestData("CoffeeItems");
    }
}
