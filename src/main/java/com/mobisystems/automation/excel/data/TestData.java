package com.mobisystems.automation.excel.data;


import com.mobisystems.automation.excel.AcceptanceTest;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class TestData extends AcceptanceTest {

    @Test(priority = 1)
    public void AddGoogleCloud() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 150);

        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Files")));
        driver.findElement(MobileBy.AccessibilityId("Files")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Dropbox")));
        driver.findElement(MobileBy.AccessibilityId("Dropbox")).tap(1, 1);

        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Allow")));
        driver.findElement(MobileBy.AccessibilityId("Allow")).tap(1, 1);

        Thread.sleep(3000);
    }
}
