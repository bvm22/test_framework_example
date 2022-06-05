package ru.example.framework.core.browser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlatformType {
	DESKTOP("1280x1024"),
	TABLET("1080x810"),
	MOBILE("420x560");
	private final String screen;
}
