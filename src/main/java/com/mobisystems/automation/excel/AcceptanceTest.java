package com.mobisystems.automation.excel;

import com.mobisystems.automation.BaseTest;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.mobisystems.automation.Utils.trimAppiumException;

public abstract class AcceptanceTest extends BaseTest {

    @BeforeSuite(dependsOnMethods = "initAppiumDriver")
    public void whatsNew() throws Exception {
        Thread.sleep(1000);
        driver.tap(1, 594, 202, 1);
        LOG.info("What's new screen was closed successfully.");
    }

    @BeforeClass
    public void clearAlerts() throws IOException, InterruptedException, NoAlertPresentException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        MobileElement alertElement = tryElement("//XCUIElementTypeAlert");
        if (alertElement != null && alertElement.isDisplayed()) {
            Alert alert = driver.switchTo().alert();
            driver.switchTo().alert().getText();
            String alertMsg = driver.switchTo().alert().getText();

            if (alertMsg.equalsIgnoreCase("iCloud has been enabled\nDo you want to move local files to iCloud?")) {
                alert.dismiss();
                LOG.info("Canceling moving files from local storage.");
                Thread.sleep(500);

            } else if (alertMsg.equalsIgnoreCase("Document Recovery\nThere was a problem with the last opened document. Do you want to recover it?")) {
                LOG.info("Document recovery was canceled.");
                alert.dismiss();
                Thread.sleep(500);

            } else if (alertMsg.equalsIgnoreCase("Send crash report?\nHelp improve OfficeSuite by sending the generated crash report.")) {
                alert.accept();
                Thread.sleep(1000);
                MobileElement txtFieldTo = driver.findElement(MobileBy.AccessibilityId("toField"));
                MobileElement txtFieldT1o = driver.findElementByXPath("//XCUIElementTypeTextField[@name='toField']");
                driver.findElementByXPath("//XCUIElementTypeButton[@name='OK']").tap(1, 1);
                Thread.sleep(1000);
                driver.findElementByXPath("//XCUIElementTypeButton[@name='Cancel']").tap(1, 1);
                wait.until(ExpectedConditions.visibilityOf(txtFieldTo));
                driver.tap(1, txtFieldTo, 1);
                txtFieldTo.sendKeys("pavel.popov@mobisystems.com");
                driver.tap(1, 615, 229, 1);
                Thread.sleep(1000);
            } else {
                throw new RuntimeException("No alerts" + alertMsg);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"deviceName", "iosVersion", "startingTab", "bundleID", "deviceUDID"})
    public void restore(ITestResult result, String deviceName, String iosVersion, String startingTab, String bundleID, String deviceUDID) throws Exception {
    String testName = String.format("%s[%s]", result.getMethod().getRealClass().getSimpleName(), result.getMethod().getMethodName());
    String timestamp = new SimpleDateFormat("hh-mm-ss").format(Calendar.getInstance().getTime());
        switch (result.getStatus()) {

            case ITestResult.SUCCESS:
                LOG.info("Test {} completed successfully", testName);
                break;

            case ITestResult.FAILURE:
                takeScreenshot(timestamp);
                LOG.error("Test {} failed", testName);
                Throwable throwable = result.getThrowable();
                if (throwable != null)
                    LOG.error("Failure cause: ", trimAppiumException(throwable));
                quitAppiumDriver();


            case ITestResult.SKIP:
                LOG.warn("Test {} skipped", testName);
                break;

            default:
                String msg = "Unexpected test status: " + result.getStatus();
                LOG.error(msg);
                throw new RuntimeException(msg);
        }
    }

    protected String button(String name) {
        return "//XCUIElementTypeButton[@name='']";
    }


}
