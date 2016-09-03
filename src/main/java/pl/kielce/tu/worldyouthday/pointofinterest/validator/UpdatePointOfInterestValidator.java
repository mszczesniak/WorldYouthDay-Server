package pl.kielce.tu.worldyouthday.pointofinterest.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategoryRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.UpdatePointOfInterestResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@Component
public class UpdatePointOfInterestValidator implements UpdateValidator<UpdatePointOfInterestResource> {

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private PointOfInterestCategoryRepository pointOfInterestCategoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(String id, UpdatePointOfInterestResource resource) throws ValidationException {
        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (!pointOfInterestRepository.exists(id)) {
            throw new ValidationException(String.format("POI with id: %s doesn't exist", id));
        }


        if (resource.getLatitude() == null) {
            throw new ValidationException("Invalid latitude");
        }

        if (resource.getLongitude() == null) {
            throw new ValidationException("Invalid longitude");
        }

        if (Strings.isNullOrEmpty(resource.getCategoryId())) {
            throw new ValidationException("Invalid category id");
        }

        if (!pointOfInterestCategoryRepository.exists(resource.getCategoryId())) {
            throw new ValidationException(String.format("POI category with id: %s doesn't exist", resource.getCategoryId()));
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
