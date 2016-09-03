package pl.kielce.tu.worldyouthday.prayer.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;


@Component
public class UpdatePrayerValidator implements UpdateValidator<UpdatePrayerResource> {

    @Autowired
    private PrayerRepository prayerRepository;

    @Autowired
    private UpdatePrayerDetailsValidator detailsValidator;

    @Override
    public void validate(String id, UpdatePrayerResource resource) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Invalid version");
        }


        if (!prayerRepository.exists(id)) {
            throw new ValidationException(String.format("Prayer with id %s not exist", id));
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

        for (UpdatePrayerDetailsResource detailsResource : resource.getDetails().values()) {
            detailsValidator.validate(detailsResource);
        }
    }
}
