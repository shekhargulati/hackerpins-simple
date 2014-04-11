package org.shekhar.business.bean_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shekhargulati on 11/04/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = {StoryExistsValidator.class})
public @interface StoryExists {

    String message() default "Story exists with given URL";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
