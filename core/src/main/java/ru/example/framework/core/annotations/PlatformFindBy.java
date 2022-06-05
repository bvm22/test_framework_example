package ru.example.framework.core.annotations;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static ru.example.framework.core.configurations.PlatformTypeParser.getPlatformTypeParser;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(PlatformFindBy.FindByBuilder.class)
public @interface PlatformFindBy {

	String desktop();

	String tablet();

	String mobile();

	class FindByBuilder extends AbstractFindByBuilder {

		public By buildIt(Object annotation, Field field) {
			PlatformFindBy platformFindBy = (PlatformFindBy) annotation;
			return switch (getPlatformTypeParser().getPlatformType()) {
				case DESKTOP -> getByFromLocator(platformFindBy.desktop());
				case TABLET -> getByFromLocator(platformFindBy.tablet());
				case MOBILE -> getByFromLocator(platformFindBy.mobile());
			};
		}

		private By getByFromLocator(String locator) {
			return isXpath(locator) ? By.xpath(locator) : By.cssSelector(locator);
		}

		private boolean isXpath(String locator) {
			return locator.startsWith("./") || locator.startsWith("/");
		}
	}
}
