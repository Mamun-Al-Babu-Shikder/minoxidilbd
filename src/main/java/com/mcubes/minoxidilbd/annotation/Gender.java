package com.mcubes.minoxidilbd.annotation;

import com.mcubes.minoxidilbd.validator.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {
    String message() default "Invalid gender type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
