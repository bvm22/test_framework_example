package ru.example.framework.core.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationProperties {

	@Value("${test.env}")
	private String testEnv;

	@Value("${test.browser}")
	private String testBrowser;

	@Value("${test.remote}")
	private boolean testRemote;

	@Value("${test.hub}")
	private String testHub;
}
