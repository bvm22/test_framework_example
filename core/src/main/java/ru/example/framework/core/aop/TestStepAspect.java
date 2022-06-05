package ru.example.framework.core.aop;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import static io.qameta.allure.util.AspectUtils.getParametersMap;
import static io.qameta.allure.util.NamingUtils.processNameTemplate;

@Aspect
@Component
public class TestStepAspect {

	private static final Logger log = LoggerFactory.getLogger(TestStepAspect.class);

	@Pointcut("@annotation(io.qameta.allure.Step)")
	private void withAnnotationStep(){}

	@Pointcut("execution(* *(..))")
	private void anyMethod(){}

	@Pointcut("execution(* *..steps.login..*(..))")
	private void loginSteps(){}

	@Before("anyMethod() && withAnnotationStep() && !loginSteps()")
	public void regularStepAdvice(JoinPoint joinPoint) {
		String stepName = getStepName(joinPoint);
		log.info("* STEP: " + stepName);
	}

	@AfterReturning("anyMethod() && withAnnotationStep() && loginSteps()")
	public void loginStepAdvice(JoinPoint joinPoint){
		String stepName = getStepName(joinPoint);
		log.info("* STEP: " + stepName);
	}

	private String getStepName(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Map<String, Object> parametersMap = getParametersMap(methodSignature, joinPoint.getArgs());
		Method method = methodSignature.getMethod();
		Step step = method.getAnnotation(Step.class);
		String stepName = step.value();

		return Optional.of(stepName)
				.filter(StringUtils::isNoneEmpty)
				.map(it -> processNameTemplate(it, parametersMap))
				.orElse(methodSignature.getName());
	}
}
