package pl.kielce.tu.worldyouthday.prayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.Prayer;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.prayer.PrayerSearchCriteria;
import pl.kielce.tu.worldyouthday.prayer.converters.PrayerToResourceConverter;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.validator.FindByIdPrayerValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;
import java.util.Optional;

@Service
public class
        FindPrayerService {

    @Autowired
    private PrayerRepository prayerRepository;

    @Autowired
    private PrayerToResourceConverter prayerToResourceConverter;

    @Autowired
    private FindByIdPrayerValidator findByIdPrayerValidator;

    public List<PrayerResource> getPrayers(PrayerSearchCriteria searchCriteria) {

        Optional<Language> language = searchCriteria.getLanguage();
        if (language.isPresent()) {
            return prayerToResourceConverter.convert(prayerRepository.findAll(), language.get());
        } else {
            return prayerToResourceConverter.convert(prayerRepository.findAll());
        }
    }

    public PrayerResource getPrayerForId(String id) throws ValidationException {

        findByIdPrayerValidator.validate(id);

        Prayer prayer = prayerRepository.findOne(id);
        return prayer != null ? prayerToResourceConverter.convert(prayer) : null;
    }


}
