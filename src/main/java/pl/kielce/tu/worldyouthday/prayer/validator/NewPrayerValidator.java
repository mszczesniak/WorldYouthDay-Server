package pl.kielce.tu.worldyouthday.prayer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class NewPrayerValidator implements Validator<NewPrayerResource> {

    @Autowired
    private NewPrayerDetailsValidator detailsValidator;

    @Override
    public void validate(NewPrayerResource resource) throws ValidationException {

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

        for (NewPrayerDetailsResource detailsResource : resource.getDetails().values()) {
            detailsValidator.validate(detailsResource);
        }
    }
}
