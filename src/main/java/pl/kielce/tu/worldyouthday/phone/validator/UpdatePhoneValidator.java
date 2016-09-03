package pl.kielce.tu.worldyouthday.phone.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.phone.PhoneRepository;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;

@Component
public class UpdatePhoneValidator implements UpdateValidator<UpdatePhoneResource> {


    @Autowired
    private UpdatePhoneDetailsValidator detailsValidator;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PhoneNumberValidator phoneNumberValidator;


    @Override
    public void validate(String id, UpdatePhoneResource resource) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Empty id");
        }

        if (!phoneRepository.exists(id)) {
            throw new ValidationException(String.format("Phone with id %s not exist", id));
        }

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

        for (UpdatePhoneDetailsResource detailsResource : resource.getDetails().values()) {
            detailsValidator.validate(detailsResource);
        }

        if (resource.getCityIds() == null) {
            throw new ValidationException("Citis is null");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Version is null");
        }

        for (String cityId : resource.getCityIds()) {
            if (!cityRepository.exists(cityId)) {
                throw new ValidationException(String.format("City with id %s not exist", cityId));
            }
        }

    }
}
