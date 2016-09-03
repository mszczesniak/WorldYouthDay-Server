package pl.kielce.tu.worldyouthday.pointofinterest.category.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategoryRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.UpdatePointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;

/**
 * Created by Łukasz Wesołowski on 13.04.2016.
 */

@Component
public class UpdatePointOfInterestCategoryValidator implements UpdateValidator<UpdatePointOfInterestCategoryResource> {
    @Autowired
    private PointOfInterestCategoryRepository pointOfInterestCategoryRepository;

    @Autowired
    private UpdatePointOfInterestCategoryDetailsValidator detailsValidator;


    @Override
    public void validate(String id, UpdatePointOfInterestCategoryResource resource) throws ValidationException {

        if (!pointOfInterestCategoryRepository.exists(id)) {
            throw new ValidationException(String.format("Category with id %s doesn't exist", id));
        }

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

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

        if (resource.getVersion() == null) {
            throw new ValidationException("Invalid version");
        }
    }
}
