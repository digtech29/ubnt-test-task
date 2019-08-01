package com.ubnt.testTask.entities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Validate enumeration values
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EnumValidator.Validator.class })
public @interface EnumValidator {

    Class<?> enumClass();

    String message() default "Incorrect enum value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<EnumValidator, String> {

        private EnumValidator validator;

        @Override
        public void initialize(EnumValidator validator) {
            this.validator = validator;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean isValid(String stringValue, ConstraintValidatorContext constraintValidatorContext) {
            try {
                Enum.valueOf((Class) validator.enumClass(), stringValue);
                return true;
            } catch (Exception e) {
                return false;
            }

        }

    }

}
