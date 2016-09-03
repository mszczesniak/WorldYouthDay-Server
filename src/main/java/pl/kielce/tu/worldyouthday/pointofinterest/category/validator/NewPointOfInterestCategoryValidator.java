package pl.kielce.tu.worldyouthday.pointofinterest.category.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.NewPointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

/**
 * Created by Łukasz Wesołowski on 12.04.2016.
 */

@Component
public class NewPointOfInterestCategoryValidator implements Validator<NewPointOfInterestCategoryResource> {

    @Autowired
    private NewPointOfInterestCategoryDetailsValidator detailsValidator;

    @Override
    public void validate(NewPointOfInterestCategoryResource resource) throws ValidationException {
        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (resource.getDetails() == null) {
            throw new ValidationException("Details not present");
        }

        if (!resource.getDetails().containsKey(Language.getDefault())) {
            throw new ValidationException("Details not present in default language");
        }

        for (Language language : resource.getDetails().keySet()) {
            if (language == null) {
                throw new ValidationException("Language is null");
            }
        }

        for (PointOfInterestCategoryDetailsResource detailsResource : resource.getDetails().values()) {
            detailsValidator.validate(detailsResource);
        }
    }
}
