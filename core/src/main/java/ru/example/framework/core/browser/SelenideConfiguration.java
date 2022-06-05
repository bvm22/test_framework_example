package ru.example.framework.core.browser;

import com.codeborne.selenide.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.framework.core.configurations.TestConfig;

@Component
public class SelenideConfiguration {

	@Autowired
	public SelenideConfiguration(TestConfig testConfig, CapabilitiesManager capabilitiesManager) {
		Configuration.browser = testConfig.getBrowser().getValue();
		Configuration.browserSize = testConfig.getPlatform().getScreen();
		Configuration.pageLoadTimeout = 45000;
		capabilitiesManager.setupCapabilities();

		if (testConfig.isRemote()) {
			Configuration.remote = testConfig.getHub();
		}
	}
}
