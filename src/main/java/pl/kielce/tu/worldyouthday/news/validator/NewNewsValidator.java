package pl.kielce.tu.worldyouthday.news.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.resource.NewNewsResource;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */

@Component
public class NewNewsValidator implements Validator<NewNewsResource> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(NewNewsResource var1) throws ValidationException {
        if (Strings.isNullOrEmpty(var1.getAuthorId())) {
            throw new ValidationException("Invalid author id");
        }

        if (!userRepository.exists(var1.getAuthorId())) {
            throw new ValidationException(String.format("User with id: %s doesn't exist", var1.getAuthorId()));
        }

        if (Strings.isNullOrEmpty(var1.getCityId())) {
            throw new ValidationException("Invalid city id");
        }

        if (!cityRepository.exists(var1.getCityId())) {
            throw new ValidationException(String.format("City with id: %s doesn't exist", var1.getCityId()));
        }

        if (var1.getDetails().size() < 1) {
            throw new ValidationException("Details not present");
        }

        if (!var1.getDetails().containsKey(Language.getDefault())) {
            throw new ValidationException("Details not present in default language");
        }
    }
}
