package com.mcubes.minoxidilbd.validator;

import com.mcubes.minoxidilbd.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public void initialize(Gender constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s!=null && s.length()!=0 && s.matches("(Male|Female|Others)");
    }
}
