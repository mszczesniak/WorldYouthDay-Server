package pl.kielce.tu.worldyouthday.prayer.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class FindByIdPrayerValidator implements Validator<String> {

    @Autowired
    private PrayerRepository prayerRepository;

    @Override
    public void validate(String id) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (!prayerRepository.exists(id)) {
            throw new ValidationException("Prayer with id not exist");
        }
    }
}
