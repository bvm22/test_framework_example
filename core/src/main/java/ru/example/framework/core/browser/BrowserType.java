package ru.example.framework.core.browser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrowserType {
	CHROME("chrome"),
	FIREFOX("firefox");

	private final String value;
}
