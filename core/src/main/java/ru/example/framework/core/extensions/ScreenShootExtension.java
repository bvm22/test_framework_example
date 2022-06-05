package ru.example.framework.core.extensions;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static java.io.File.separator;


public class ScreenShootExtension implements BeforeEachCallback, AfterEachCallback {

	private static final String USER_DIR = System.getProperty("user.dir");

	public ScreenShootExtension() {

		Configuration.reportsFolder = StringUtils.substringBeforeLast(USER_DIR, separator)
				+ separator + "target" + separator + "attachments";
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
				.screenshots(true)
				.savePageSource(true));
	}

	public void beforeEach(ExtensionContext context) {
		Optional<Class<?>> testClass = context.getTestClass();
		String className = testClass.map(Class::getName).orElse("EmptyClass");
		Optional<Method> testMethod = context.getTestMethod();
		String methodName = testMethod.map(Method::getName).orElse("emptyMethod");
		Screenshots.startContext(className, methodName);
	}

	public void afterEach(ExtensionContext context) {
		context.getExecutionException().ifPresent((error) -> {
			if (!(error instanceof UIAssertionError)) {
				byte[] screen = Selenide.screenshot(OutputType.BYTES);
				byte[] page = WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
				Allure.getLifecycle().addAttachment("Screenshot", "image/png", ".png", screen);
				Allure.getLifecycle().addAttachment("Page Source", "text/html", ".html", page);
			}
		});
		Screenshots.finishContext();
	}
}
