package pl.kielce.tu.worldyouthday.pointofinterest.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@Component
public class NewPointOfInterestDetailsValidator implements Validator<PointOfInterestDetailsResource> {
    @Override
    public void validate(PointOfInterestDetailsResource resource) throws ValidationException {
        if (Strings.isNullOrEmpty(resource.getName())) {
            throw new ValidationException("Invalid name");
        }

        if (Strings.isNullOrEmpty(resource.getDescription())) {
            throw new ValidationException("Invalid description");
        }
    }
}
