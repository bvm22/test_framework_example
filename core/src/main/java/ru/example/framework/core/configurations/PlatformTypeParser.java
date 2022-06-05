package ru.example.framework.core.configurations;

import org.apache.commons.lang3.StringUtils;
import ru.example.framework.core.browser.PlatformType;

import static org.apache.commons.lang3.EnumUtils.isValidEnumIgnoreCase;

public class PlatformTypeParser {

	private static final String USER_DIR = System.getProperty("user.dir");
	private static PlatformTypeParser instance;

	private final PlatformType platformType;

	private PlatformTypeParser() {
		String moduleName = StringUtils.substringAfterLast(USER_DIR, "/");
		String platformName = StringUtils.substringBefore(moduleName, "_");
		validatePlatformType(platformName);
		platformType = PlatformType.valueOf(platformName.toUpperCase());
	}

	public static PlatformTypeParser getPlatformTypeParser() {
		if (instance == null) {
			instance = new PlatformTypeParser();
		}
		return instance;
	}

	public PlatformType getPlatformType() {
		return platformType;
	}

	private void validatePlatformType(String platformName) {
		if (!isValidEnumIgnoreCase(PlatformType.class, platformName)) {
			throw new IllegalArgumentException("platform is incorrect. Can't init platform from platformName " + platformName);
		}
	}
}
