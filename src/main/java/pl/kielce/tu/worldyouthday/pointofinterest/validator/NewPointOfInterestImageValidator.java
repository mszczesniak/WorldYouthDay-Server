package pl.kielce.tu.worldyouthday.pointofinterest.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 11.05.2016.
 */

@Component
public class NewPointOfInterestImageValidator {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    public void validate(String poiId, MultipartFile file) throws ValidationException {
        if (Strings.isNullOrEmpty(poiId)) {
            throw new ValidationException("Invalid POI id");
        }

        if (!pointOfInterestRepository.exists(poiId)) {
            throw new ValidationException(String.format("POI with id: %s doesn't exist", poiId));
        }

        if (file == null) {
            throw new ValidationException("File is empty");
        }
    }
}
