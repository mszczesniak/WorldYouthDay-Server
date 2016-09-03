package pl.kielce.tu.worldyouthday.utils.tag.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.tag.PrayerTag;
import pl.kielce.tu.worldyouthday.utils.tag.parser.PrayerTagParser;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@Component
public class PrayerTagValidator implements TagValidator {
    @Autowired
    private PrayerTagParser prayerTagParser;

    @Autowired
    private PrayerRepository prayerRepository;

    @Override
    public void validate(String text) throws ValidationException {
        List<PrayerTag> prayerTags = prayerTagParser.parse(text);

        for (PrayerTag p : prayerTags) {
            checkTag(p);
        }
    }

    private void checkTag(PrayerTag tag) throws ValidationException {
        if (Strings.isNullOrEmpty(tag.getId())) {
            throw new ValidationException("Invalid id");
        }

        if (!prayerRepository.exists(tag.getId())) {
            throw new ValidationException(String.format("Prayer with id: %s doesn't exist", tag.getId()));
        }
    }
}
