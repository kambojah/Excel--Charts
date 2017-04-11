package com.mobisystems.automation;

import com.google.common.io.Files;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static com.mobisystems.automation.Utils.trimAppiumException;

    @Listeners(ConfigurationListener.class)
    public abstract class BaseTest {

    protected static Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    protected static AppiumServer server;
    protected static IOSDriver<MobileElement> driver;

    protected static Properties localeProps;
    private static String outputPath;

    protected static int screenshotsCount;
    protected static boolean screenshotsEnabled = true;


    @BeforeSuite
    protected void initAppiumServer() throws IOException {
        server = new AppiumServer();
    }

    @BeforeSuite
    @Parameters("outputPath")
    protected void setOutputPath(String path) {
        outputPath = path;
        screenshotsCount = 0;
    }

        @BeforeSuite
        @Parameters({"localePath", "language"})
        protected void loadLocale(String localePath, String language) throws IOException {
            localeProps = Utils.readStringsFile(String.format("%s/%s.lproj/Localizable.strings", localePath, language));
            LOG.info("Loaded locale: {}", language);
        }

        @BeforeSuite(dependsOnMethods = "initAppiumServer")
        @Parameters({"deviceName", "iosVersion", "deviceUDID", "bundleID", "automationName"})
        protected void initAppiumDriver(String deviceName, String iosVersion, String deviceUDID, String bundleID, String automationName) {
            File appDir = new File(System.getProperty("user.dir"));
            File app = new File(appDir, "OfficeSuite.app");

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.3.1");
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, "QA Air 2");
            caps.setCapability(MobileCapabilityType.UDID, "1ff3ca8785493b460ccbdab2fc913e9e0b58032a");
            caps.setCapability(MobileCapabilityType.APP, app);
            caps.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
            caps.setCapability(MobileCapabilityType.FULL_RESET, true);
            driver = new IOSDriver<MobileElement>(server.url, caps);
            LOG.info("Appium driver initialized successfully");
            LOG.info("Desired capabilities: " + caps);
    }

    @AfterSuite(alwaysRun = true)
    protected void quitAppiumDriver() {
        try {
            driver.quit();
        }
        catch (Exception e) {
            if (!e.getMessage().contains("Instruments did not terminate"))
                LOG.warn("Quitting Appium driver failed", trimAppiumException(e));
        }
    }

        @AfterSuite(alwaysRun = true)
        public void logCount() {
        LOG.info("{} screenshots taken", screenshotsCount);
    }

        @AfterSuite(alwaysRun = true)
        protected void stopAppiumServer() {
            server.stop();
        }

        protected File takeScreenshot(String name) throws IOException, InterruptedException {
        if (screenshotsEnabled) {
            Thread.sleep(200);
            File result = driver.getScreenshotAs(OutputType.FILE);
            File destination = new File("/Users/pavelpopov/Desktop/Automation/Excel/Charts/PNG/Actual" + "/" + name + ".PNG");
            Files.createParentDirs(destination);
            Files.move(result, destination);
            screenshotsCount++;
        }
            return null;
        }

       protected By selector(String query) {
        if (query.startsWith("/"))
            return By.xpath(query);
        else
            return By.name(query);
    }

        protected boolean isVisible (String query) {
        MobileElement element = tryElement(query);
        return element != null && element.isDisplayed();
    }

        protected MobileElement element(String query) {
        return driver.findElement(selector(query));
    }

        protected MobileElement tryElement(String query) {
        try {
            return element(query);
        } catch (Exception e) {
            return null;
        }
    }
        protected void click(String query) {
        try {
            element(query).click();
        } catch (WebDriverException e) {
            LOG.warn("click(\"{}\") failed", query);
            query = transformQuery(query);
            LOG.info("Repeating click(\"{}\")", query);
            element(query).click();
        }
    }

        // Ugly fix due to Appium being buggy. Same
        // semantic query fails via By.name and
        // succeeds via By.xpath and vice versa ...
        protected String transformQuery(String query) {
        if (query.startsWith("/")) {
            String[] parts = query.split("/");
            String el = parts[parts.length - 1];
            if (el.contains("@name") && !el.contains("][")) {
                el = el.substring(el.indexOf("=") + 1, el.indexOf("]")).trim();
                el = el.substring(1, el.length() - 1);
                LOG.debug("Transformed By.xpath({}) to By.name({})", query, el);
                return el;
            }
        } else {
            String xpath = "//*[@name='" + query + "']";
            LOG.debug("Transformed By.name({}) to By.xpath({})", query, xpath);
            return xpath;
        }
        LOG.debug("Unable to transform query: {}", query);
        return query;
    }

        protected String locale (String key) {
        return localeProps.getProperty(key);
    }

}



