package com.ssafy.a302.global.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ssafy.a302.global.validator.EnumValidator.*;

@Constraint(validatedBy = {EnumValidatorImpl.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {

    String message() default "올바르지 않은 요청입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;

    class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {
        private EnumValidator annotation;

        @Override
        public void initialize(EnumValidator constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            boolean result = false;
            Object[] enumValues = this.annotation.enumClass().getEnumConstants();
            if (enumValues != null) {
                for (Object enumValue : enumValues) {
                    if (value.equals(enumValue.toString())
                            || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                        result = true;
                        break;
                    }
                }
            }
            return result;
        }
    }
}
