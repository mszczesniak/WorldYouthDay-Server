package pl.kielce.tu.worldyouthday.phone.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class NewPhoneValidator implements Validator<NewPhoneResource> {

    @Autowired
    private AddPhoneDetailsValidator detailsValidator;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PhoneNumberValidator phoneNumberValidator;

    @Override
    public void validate(NewPhoneResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        phoneNumberValidator.validate(resource.getNumber());

        if (resource.getDetails() == null) {
            throw new ValidationException("Details is null");
        }

        if (!resource.getDetails().containsKey(Language.getDefault())) {
            throw new ValidationException("Details not present in default language");
        }

        for (Language language : resource.getDetails().keySet()) {
            if (language == null) {
                throw new ValidationException("Language is null");
            }
        }

        for (NewPhoneDetailsResource detailsResource : resource.getDetails().values()) {
            detailsValidator.validate(detailsResource);
        }

        if (resource.getCityIds() == null) {
            throw new ValidationException("Citis is null");
        }

        for (String cityId : resource.getCityIds()) {
            if (!cityRepository.exists(cityId)) {
                throw new ValidationException(String.format("City with id %s not exist", cityId));
            }
        }

    }
}
