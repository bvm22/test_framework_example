package ru.example.framework.core.configurations;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.framework.core.browser.BrowserType;
import ru.example.framework.core.browser.PlatformType;

import static ru.example.framework.core.configurations.PlatformTypeParser.getPlatformTypeParser;

@Component
public class TestConfigImpl implements TestConfig {

	private final String env;
	private final BrowserType browser;
	private final boolean isRemote;
	private final String hub;
	private final PlatformType platformType;

	private final ApplicationProperties applicationProperties;

	@Autowired
	public TestConfigImpl(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;

		validateProperties();

		env = applicationProperties.getTestEnv();
		browser = BrowserType.valueOf(applicationProperties.getTestBrowser().toUpperCase());
		isRemote = applicationProperties.isTestRemote();
		hub = applicationProperties.getTestHub();
		platformType = getPlatformTypeParser().getPlatformType();
	}

	private void validateProperties() {
		validateEnvProperty();
		validateBrowserProperty();
		validateHubProperty();
	}

	private void validateEnvProperty() {
		String testEnv = applicationProperties.getTestEnv();
		if (StringUtils.isBlank(testEnv)) {
			throw new IllegalArgumentException("test.env is blank. Setup correct value.");
		}
	}

	private void validateBrowserProperty() {
		String testBrowser = applicationProperties.getTestBrowser();
		if (!StringUtils.equalsAny(testBrowser, BrowserType.CHROME.getValue(), BrowserType.FIREFOX.getValue())) {
			throw new IllegalArgumentException("test.browser incorrect. Setup \"chrome\" or \"firefox\". Current value is " + testBrowser);
		}
	}

	private void validateHubProperty() {
		String testHub = applicationProperties.getTestHub();
		if (applicationProperties.isTestRemote() && StringUtils.isBlank(testHub)) {
			throw new IllegalArgumentException("test.hub is blank. Setup correct value.");
		}
	}

	@Override
	public String getEnv() {
		return env;
	}

	@Override
	public BrowserType getBrowser() {
		return browser;
	}

	@Override
	public boolean isRemote() {
		return isRemote;
	}

	@Override
	public String getHub() {
		return hub;
	}

	@Override
	public PlatformType getPlatform() {
		return platformType;
	}
}
