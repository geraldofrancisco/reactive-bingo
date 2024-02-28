package com.geraldo.reactivebingo.rest.controller;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;


public abstract class ControllerTest {
    @Autowired
    public WebTestClient client;
    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    protected Set<ConstraintViolation<Object>> violation(final Object request) {
        return validator.validate(request);
    }
}
