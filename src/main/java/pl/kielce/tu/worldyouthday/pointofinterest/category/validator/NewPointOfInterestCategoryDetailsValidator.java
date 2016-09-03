package pl.kielce.tu.worldyouthday.pointofinterest.category.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class NewPointOfInterestCategoryDetailsValidator implements Validator<PointOfInterestCategoryDetailsResource> {

    @Override
    public void validate(PointOfInterestCategoryDetailsResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (Strings.isNullOrEmpty(resource.getName())) {
            throw new ValidationException("Invalid name");
        }
    }
}
