package com.mobisystems.automation;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsoleRunner {

    static Logger LOG = LoggerFactory.getLogger(ConsoleRunner.class);

    static String localePath, deviceName, iosVersion, bundleID, deviceUDID, automationName, platformName;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        Properties props = new Properties();
        String dir = new String(System.getProperty("user.dir"));
        InputStream in = new FileInputStream(dir + "/" + args[0]);
        props.load(in);
        in.close();

        platformName     = props.getProperty("platform.name");
        automationName   = props.getProperty("automation.name");
        bundleID         = props.getProperty("bundle.id");
        deviceUDID       = props.getProperty("device.udid");
        localePath       = props.getProperty("app.strings.path");
        deviceName       = props.getProperty("device.name");
        iosVersion       = props.getProperty("device.platform");

        String[] languages = props.getProperty("device.languages", "en").split(",");
        String timestamp = new SimpleDateFormat("hh-mm-ss").format(Calendar.getInstance().getTime());
        String basePath = String.format("%s/[%s] iOS %s (%s)/", System.getProperty("user.dir"), deviceName, iosVersion, timestamp);

        for (String lang : languages) {
            String path = basePath + "/" + lang;
            Files.createParentDirs(new File(path));
            try {
                LOG.info("Starting TestNG run for language {}", lang);
                run(path, lang);
            }
            catch (Exception e) {
                LOG.error("TestNG run failed: {}", e.getMessage());
            }
        }
    }

    private static void run(String path, String language) throws IOException, SAXException, ParserConfigurationException {


        InputStream xmlSuiteStream = ConsoleRunner.class.getResourceAsStream("testng.xml");
        List<XmlSuite> suite = (List<XmlSuite>) new Parser(xmlSuiteStream).parse();
        Map<String, String> params = suite.get(0).getParameters();

        params.put("automationName", automationName);
        params.put("bundleID", bundleID);
        params.put("deviceUDID", deviceUDID);
        params.put("deviceName", deviceName);
        params.put("iosVersion", iosVersion);
        params.put("platformName", platformName);
        params.put("outputPath", path + "/screenshots");

        TestNG testng = new TestNG();
        testng.setXmlSuites(suite);
        testng.setOutputDirectory(path + "/report");
        testng.run();
    }
}
