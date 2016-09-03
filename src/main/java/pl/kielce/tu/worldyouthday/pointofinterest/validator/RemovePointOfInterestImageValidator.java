package pl.kielce.tu.worldyouthday.pointofinterest.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.file.File;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.RemovePointOfInterestImageResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 17.05.2016.
 */

@Component
public class RemovePointOfInterestImageValidator implements Validator<RemovePointOfInterestImageResource> {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Override
    public void validate(RemovePointOfInterestImageResource var1) throws ValidationException {
        if (Strings.isNullOrEmpty(var1.getPointOfInterestId())) {
            throw new ValidationException("Invalid POI id");
        }

        if (Strings.isNullOrEmpty(var1.getImageId())) {
            throw new ValidationException("Invalid image id");
        }

        PointOfInterest pointOfInterest = pointOfInterestRepository.findOne(var1.getPointOfInterestId());
        if (pointOfInterest == null) {
            throw new ValidationException(String.format("POI with id: %s doesn't exist", var1.getPointOfInterestId()));
        }

        Optional<File> imageToRemove = pointOfInterest.getImages().stream().filter(i -> i.getId().equals(var1.getImageId())).findFirst();
        if (!imageToRemove.isPresent()) {
            throw new ValidationException(String.format("Image with id: %s isn't attached to POI with id: %s", var1.getImageId(), var1.getPointOfInterestId()));
        }
    }
}
