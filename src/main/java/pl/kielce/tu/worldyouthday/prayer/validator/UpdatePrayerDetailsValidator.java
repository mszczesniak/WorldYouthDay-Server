package pl.kielce.tu.worldyouthday.prayer.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class UpdatePrayerDetailsValidator implements Validator<UpdatePrayerDetailsResource> {
    @Override
    public void validate(UpdatePrayerDetailsResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (Strings.isNullOrEmpty(resource.getTitle())) {
            throw new ValidationException("Invalid title");
        }

        if (Strings.isNullOrEmpty(resource.getContent())) {
            throw new ValidationException("Invalid content");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Invalid version");
        }
    }
}
