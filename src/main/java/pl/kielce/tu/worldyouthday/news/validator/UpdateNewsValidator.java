package pl.kielce.tu.worldyouthday.news.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.NewsRepository;
import pl.kielce.tu.worldyouthday.news.resource.UpdateNewsResource;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */

@Component
public class UpdateNewsValidator implements UpdateValidator<UpdateNewsResource> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public void validate(String id, UpdateNewsResource resource) throws ValidationException {
        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (!newsRepository.exists(id)) {
            throw new ValidationException(String.format("News with id: %s doesn't exist", id));
        }

        if (Strings.isNullOrEmpty(resource.getAuthorId())) {
            throw new ValidationException("Invalid author id");
        }

        if (!userRepository.exists(resource.getAuthorId())) {
            throw new ValidationException(String.format("User with id: %s doesn't exist", resource.getAuthorId()));
        }

        if (Strings.isNullOrEmpty(resource.getCityId())) {
            throw new ValidationException("Invalid city id");
        }

        if (!cityRepository.exists(resource.getCityId())) {
            throw new ValidationException(String.format("City with id: %s doesn't exist", resource.getCityId()));
        }

        if (resource.getDetails().size() < 1) {
            throw new ValidationException("Details not present");
        }

        if (!resource.getDetails().containsKey(Language.getDefault())) {
            throw new ValidationException("Details not present in default language");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Invalid version");
        }
    }
}
