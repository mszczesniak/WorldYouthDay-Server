package pl.kielce.tu.worldyouthday.utils.tag.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.tag.LocationTag;
import pl.kielce.tu.worldyouthday.utils.tag.parser.LocationTagParser;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@Component
public class LocationTagValidator implements TagValidator {
    @Autowired
    private LocationTagParser locationTagParser;

    @Override
    public void validate(String text) throws ValidationException {
        try {
            List<LocationTag> locationTags = locationTagParser.parse(text);

            for (LocationTag p : locationTags) {
                checkTag(p);
            }
        } catch (NumberFormatException ex) {
            throw new ValidationException(ex);
        }
    }

    private void checkTag(LocationTag tag) throws ValidationException {
        if (tag.getLatitude() == null) {
            throw new ValidationException("Invalid latitude");
        }

        if (tag.getLongitude() == null) {
            throw new ValidationException("Invalid longitude");
        }
    }
}
