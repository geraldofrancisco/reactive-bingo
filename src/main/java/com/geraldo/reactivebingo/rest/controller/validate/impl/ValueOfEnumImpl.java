package com.geraldo.reactivebingo.rest.controller.validate.impl;

import com.geraldo.reactivebingo.rest.controller.validate.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ValueOfEnumImpl implements ConstraintValidator<ValueOfEnum, String> {

    List<String> values;

    @Override
    public void initialize(ValueOfEnum constraint) {
        this.values = Arrays.stream(constraint.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isEmpty(value) ||
            this.values.stream().anyMatch(s -> s.equals(value));
    }
}
