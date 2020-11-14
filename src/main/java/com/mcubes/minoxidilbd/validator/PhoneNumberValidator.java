package com.mcubes.minoxidilbd.validator;

import com.mcubes.minoxidilbd.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s!=null && s.length()!=0 && s.matches("(\\+8801|01)[3456789][0-9]{8}");
    }
}
