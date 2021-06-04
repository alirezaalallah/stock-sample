package com.example.stocksample.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StockIdValidator.class)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StockId {
    String message() default "Stock id should be digit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
