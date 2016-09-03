package pl.kielce.tu.worldyouthday.utils.tag.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.tag.PointOfInterestTag;
import pl.kielce.tu.worldyouthday.utils.tag.parser.PointOfInterestTagParser;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@Component
public class PointOfInterestTagValidator implements TagValidator {
    @Autowired
    private PointOfInterestTagParser pointOfInterestTagParser;

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Override
    public void validate(String text) throws ValidationException {
        List<PointOfInterestTag> prayerTags = pointOfInterestTagParser.parse(text);

        for (PointOfInterestTag p : prayerTags) {
            checkTag(p);
        }
    }

    private void checkTag(PointOfInterestTag tag) throws ValidationException {
        if (Strings.isNullOrEmpty(tag.getId())) {
            throw new ValidationException("Invalid id");
        }

        if (!pointOfInterestRepository.exists(tag.getId())) {
            throw new ValidationException(String.format("POI with id: %s doesn't exist", tag.getId()));
        }
    }
}
