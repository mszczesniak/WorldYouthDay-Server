package pl.kielce.tu.worldyouthday.scheduler.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.scheduler.resources.UpdateEventDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class UpdateEventDetailsValidator implements Validator<UpdateEventDetailsResource> {
    @Override
    public void validate(UpdateEventDetailsResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (Strings.isNullOrEmpty(resource.getTitle())) {
            throw new ValidationException("Invalid title");
        }

        if (Strings.isNullOrEmpty(resource.getDescription())) {
            throw new ValidationException("Invalid content");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Invalid version");
        }
    }
}
