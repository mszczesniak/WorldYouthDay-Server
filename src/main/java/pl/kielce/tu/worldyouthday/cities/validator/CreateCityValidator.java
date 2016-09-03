package pl.kielce.tu.worldyouthday.cities.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.cities.resources.NewCityResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class CreateCityValidator implements Validator<NewCityResource> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(NewCityResource resource) throws ValidationException {

        if (resource == null) {
            throw new ValidationException("Resource is null");
        }

        if (cityRepository.findOneByName(resource.getName()).isPresent()) {
            throw new ValidationException("Name exist");
        }

        if (Strings.isNullOrEmpty(resource.getName())) {
            throw new ValidationException("Invalid name");
        }
    }
}
