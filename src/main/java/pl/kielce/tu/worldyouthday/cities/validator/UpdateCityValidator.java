package pl.kielce.tu.worldyouthday.cities.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.cities.resources.UpdateCityResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;

@Component
public class UpdateCityValidator implements UpdateValidator<UpdateCityResource> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(String id, UpdateCityResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (cityRepository.findOne(id) == null) {
            throw new ValidationException(String.format("City with id %s not exist", id));
        }

        if (Strings.isNullOrEmpty(resource.getName())) {
            throw new ValidationException("Invalid name");
        }

        if (cityRepository.findOneByName(resource.getName()).isPresent()) {
            throw new ValidationException("Name exist");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Invalid version");
        }

    }
}
