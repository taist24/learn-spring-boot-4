package com.learnspringboot.week2.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private Set<String> acceptedValues;
    private List<String> displayValues;
    private boolean required;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        displayValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();

        acceptedValues = displayValues.stream()
                .collect(Collectors.toUnmodifiableSet());

        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (required == true && value == null) {
            return false;
        } else if (required == false && value == null) {
            return true;
        }

        if (acceptedValues.contains(value)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("must be one of: " + String.join(", ", displayValues))
                .addConstraintViolation();

        return false;
    }

}
