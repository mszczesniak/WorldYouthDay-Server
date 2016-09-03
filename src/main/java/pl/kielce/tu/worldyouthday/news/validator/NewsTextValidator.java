package pl.kielce.tu.worldyouthday.news.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.tag.validator.EventTagValidator;
import pl.kielce.tu.worldyouthday.utils.tag.validator.LocationTagValidator;
import pl.kielce.tu.worldyouthday.utils.tag.validator.PointOfInterestTagValidator;
import pl.kielce.tu.worldyouthday.utils.tag.validator.PrayerTagValidator;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

/**
 * Created by Łukasz Wesołowski on 06.05.2016.
 */

@Component
public class NewsTextValidator implements Validator<String> {
    @Autowired
    private PointOfInterestTagValidator pointOfInterestTagValidator;

    @Autowired
    private LocationTagValidator locationTagValidator;

    @Autowired
    private PrayerTagValidator prayerTagValidator;

    @Autowired
    private EventTagValidator eventTagValidator;

    @Override
    public void validate(String text) throws ValidationException {
        pointOfInterestTagValidator.validate(text);
        prayerTagValidator.validate(text);
        locationTagValidator.validate(text);
        eventTagValidator.validate(text);
    }
}
