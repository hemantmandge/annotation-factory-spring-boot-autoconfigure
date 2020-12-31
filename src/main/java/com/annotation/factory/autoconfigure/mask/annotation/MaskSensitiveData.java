package com.annotation.factory.autoconfigure.mask.annotation;

import com.annotation.factory.autoconfigure.mask.enums.MaskingStrategies;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskSensitiveData {
    MaskingStrategies strategy() default MaskingStrategies.MASK_ALL;
    int numberOfCharacters() default 0;
}
