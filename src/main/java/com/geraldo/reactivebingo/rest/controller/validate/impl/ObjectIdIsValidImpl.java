package com.geraldo.reactivebingo.rest.controller.validate.impl;

import com.geraldo.reactivebingo.rest.controller.validate.ObjectIdIsValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;

public class ObjectIdIsValidImpl implements ConstraintValidator<ObjectIdIsValid, String> {
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return ObjectId.isValid(id);
    }
}
