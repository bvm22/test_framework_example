package ru.example.framework.core.annotations;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import ru.example.framework.core.extensions.ScreenShootExtension;
import ru.example.framework.App;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(classes = App.class)
@ExtendWith({ScreenShootExtension.class})
public @interface SelenideTest {

}
