package ru.example.framework.core.configurations;

import ru.example.framework.core.browser.BrowserType;
import ru.example.framework.core.browser.PlatformType;

public interface TestConfig {
	String getEnv();
	BrowserType getBrowser();
	boolean isRemote();
	String getHub();
	PlatformType getPlatform();
}
