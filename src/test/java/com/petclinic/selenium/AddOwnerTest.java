package com.petclinic.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddOwnerTest extends BaseTest {

    @Test
    public void testAddOwnerPage() {
        driver.findElement(By.linkText("Owners")).click();
        driver.findElement(By.linkText("Add Owner")).click();
        Assert.assertTrue(driver.getPageSource().contains("New Owner"));
    }
}
