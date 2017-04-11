package com.mobisystems.automation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IConfigurationListener;
import org.testng.ITestResult;

import static com.mobisystems.automation.Utils.trimAppiumException;

public class ConfigurationListener implements IConfigurationListener {

	private static Logger LOG = LoggerFactory.getLogger(ConfigurationListener.class);

	@Override
	public void onConfigurationSuccess(ITestResult iTestResult) {

	}

	@Override
	public void onConfigurationFailure(ITestResult result) {
		LOG.error("Configuration method {}[{}] failed",
				result.getTestClass().getRealClass().getSimpleName(),
				result.getName(),
				trimAppiumException(result.getThrowable()));
	}

	@Override
	public void onConfigurationSkip(ITestResult result) {
		LOG.warn("Configuration method {}[{}] skipped",
				result.getTestClass().getRealClass().getSimpleName(),
				result.getName());
	}
}
