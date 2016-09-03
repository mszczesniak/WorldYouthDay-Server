package pl.kielce.tu.worldyouthday.scheduler.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventDetailsResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class NewEventValidator implements Validator<NewEventResource> {

    @Autowired
    private NewEventDetailsValidator detailsValidator;

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(NewEventResource resource) throws ValidationException {

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

        for (NewEventDetailsResource detailsResource : resource.getDetails().values()) {
            detailsValidator.validate(detailsResource);
        }

        for (String locationId : resource.getLocationsId()) {
            if (Strings.isNullOrEmpty(locationId)) {
                throw new ValidationException(String.format("Invalid location id %s", locationId));
            }

            if (!pointOfInterestRepository.exists(locationId)) {
                throw new ValidationException(String.format("Location with id %s not exist", locationId));
            }
        }

        if (Strings.isNullOrEmpty(resource.getCityId())) {
            throw new ValidationException("Invalid city id");
        }

        if (!cityRepository.exists(resource.getCityId())) {
            throw new ValidationException(String.format("City with id %s not exist", resource.getCityId()));
        }

        if (resource.getStartDate() == null) {
            throw new ValidationException("Invalid start date");
        }

        if (resource.getEndDate() == null) {
            throw new ValidationException("Invalid end date");
        }

        if (resource.getStartDate().after(resource.getEndDate())) {
            throw new ValidationException("End date must be after start date");
        }
    }
}
