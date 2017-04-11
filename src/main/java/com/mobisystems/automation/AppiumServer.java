package com.mobisystems.automation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class AppiumServer {

    public static final int DEFAULT_PORT = 4723;
    public final URL url;

    protected static final Logger LOG = LoggerFactory.getLogger(AppiumServer.class);
    protected final Process process;

    public AppiumServer() throws IOException {
        this(DEFAULT_PORT);
    }

    public AppiumServer(int port) throws IOException {
        String cmd = new StringBuilder().append(appiumPath())
                .append(" --port " + port)
                .append(" --full-reset")
                .append(" --session-override")
                .append(" --command-timeout 600000000")
                .append(" --log-level info")
                .toString();

        process = Runtime.getRuntime().exec(cmd);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        int lines = 0;
        String line = reader.readLine();
        StringBuilder history = new StringBuilder();
        while (!line.contains("http interface listener started on")) {
            history.append(line + "\n");
            if (lines++ > 35 || line.toLowerCase().contains("error")) {
                LOG.error("Unable to start Appium server. Appium output:" + history);
                throw new RuntimeException("Unable to start Appium server");
            }
            line = reader.readLine();
        }

        url = new URL(String.format("http://127.0.0.1:4723/wd/hub", line.split("started on")[1].trim()));
        LOG.info("Appium REST http interface started on " + url);
    }

    public void stop() {
        process.destroy();
    }

    private String appiumPath() {
        String path = "appium";
        if (isValidPath(path))
            return path;
        path = System.getProperty("user.home") + "/node_modules/.bin/appium";
        if (isValidPath(path))
            return path;
        throw new RuntimeException("Unable to find Appium path");
    }

    private boolean isValidPath(String path) {
        try {
            Process process = Runtime.getRuntime().exec("which " + path);
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            LOG.error("Failure during path resolving", e);
            return false;
        }
    }
}
