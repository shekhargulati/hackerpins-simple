package org.shekhar.business.bean_validation;

import org.shekhar.business.domain.Story;
import org.shekhar.business.services.StoryService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * Created by shekhargulati on 11/04/14.
 */
public class StoryExistsValidator implements ConstraintValidator<StoryExists, Story> {

    @Inject
    private StoryService storyService;

    @Override
    public void initialize(StoryExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(Story story, ConstraintValidatorContext context) {
        if(story == null){
            return true;
        }
        return storyService.exists(story.getUrl()) ? false : true;
    }
}
