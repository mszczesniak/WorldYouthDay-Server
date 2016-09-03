package pl.kielce.tu.worldyouthday.cities.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class RemoveCityValidator implements Validator<String> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(String cityId) throws ValidationException {

        if (Strings.isNullOrEmpty(cityId)) {
            throw new ValidationException("id");
        }

        if (!cityRepository.exists(cityId)) {
            throw new ValidationException(String.format("City with id %s not exist", cityId));
        }
    }
}
