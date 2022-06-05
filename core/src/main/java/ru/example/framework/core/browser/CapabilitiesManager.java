package ru.example.framework.core.browser;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.framework.core.configurations.TestConfig;

import java.util.Map;

import static ru.example.framework.core.browser.PlatformType.*;
import static ru.example.framework.core.browser.BrowserType.*;

@Component
public class CapabilitiesManager {

	private static final String MOBILE_DEVICE = "iPhone 6/7/8";
	private static final String TABLET_DEVICE = "iPad";
	private static final String MOBILE_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) FxiOS/40.0 Mobile/15E148 Safari/605.1.15";
	private static final String TABLET_AGENT = "Mozilla/5.0 (iPad; CPU OS 14_7_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Mobile/15E148 Safari/604.1";

	@Autowired private TestConfig testConfig;

	public void setupCapabilities() {
		switch (testConfig.getPlatform()) {
			case TABLET -> setupTabletCapabilities();
			case MOBILE -> setupMobileCapabilities();
		}
	}

	private void setupTabletCapabilities() {
		switch (testConfig.getBrowser()) {
			case CHROME -> setupMobileEmulation(TABLET_DEVICE);
			case FIREFOX -> setupUserAgent(TABLET_AGENT);
		}
	}

	private void setupMobileCapabilities() {
		switch (testConfig.getBrowser()) {
			case CHROME -> setupMobileEmulation(MOBILE_DEVICE);
			case FIREFOX -> setupUserAgent(MOBILE_AGENT);
		}
	}

	//Use only for chrome!
	private void setupMobileEmulation(String device) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", Map.of("deviceName", device));
		Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	}

	//Use only for firefox!
	private void setupUserAgent(String userAgent) {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addPreference("general.useragent.override", userAgent);
		Configuration.browserCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
	}
}
