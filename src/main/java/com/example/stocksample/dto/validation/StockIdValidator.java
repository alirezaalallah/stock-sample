package com.example.stocksample.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class StockIdValidator implements ConstraintValidator<StockId, String> {

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(id).isPresent() && id.matches("[0-9]+");
    }

    @Override
    public void initialize(StockId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
