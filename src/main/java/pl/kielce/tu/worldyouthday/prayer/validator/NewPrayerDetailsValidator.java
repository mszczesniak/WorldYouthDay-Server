package pl.kielce.tu.worldyouthday.prayer.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class NewPrayerDetailsValidator implements Validator<NewPrayerDetailsResource> {
    @Override
    public void validate(NewPrayerDetailsResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (Strings.isNullOrEmpty(resource.getTitle())) {
            throw new ValidationException("Invalid title");
        }

        if (Strings.isNullOrEmpty(resource.getContent())) {
            throw new ValidationException("Invalid content");
        }
    }
}
