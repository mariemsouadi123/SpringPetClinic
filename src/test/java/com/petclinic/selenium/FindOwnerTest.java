package com.petclinic.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindOwnerTest extends BaseTest {

    @Test
    public void testFindOwnerPage() {
        driver.findElement(By.linkText("Owners")).click();
        driver.findElement(By.linkText("Find Owners")).click();
        Assert.assertTrue(driver.getPageSource().contains("Find Owners"));
    }
}
