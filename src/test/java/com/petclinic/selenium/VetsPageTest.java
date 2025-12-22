package com.petclinic.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VetsPageTest extends BaseTest {

    @Test
    public void testVetsPage() {
        driver.findElement(By.linkText("Veterinarians")).click();
        Assert.assertTrue(driver.getPageSource().contains("Veterinarians"));
    }
}
