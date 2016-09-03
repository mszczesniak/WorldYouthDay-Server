package pl.kielce.tu.worldyouthday.prayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.Prayer;
import pl.kielce.tu.worldyouthday.prayer.PrayerDetails;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.prayer.converters.PrayerToResourceConverter;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerResource;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.validator.NewPrayerValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AddPrayerService {

    @Autowired
    private PrayerRepository prayerRepository;

    @Autowired
    private PrayerToResourceConverter prayerToResourceConverter;

    @Autowired
    private NewPrayerValidator newPrayerValidator;

    @Transactional
    @Versionable(table = VersionTable.PRAYERS)
    public PrayerResource addPrayer(NewPrayerResource newPrayerResource) throws ValidationException {

        newPrayerValidator.validate(newPrayerResource);

        Map<Language, PrayerDetails> details = new HashMap<>();
        String prayerId = UUID.randomUUID().toString();

        for (HashMap.Entry<Language, NewPrayerDetailsResource> entry : newPrayerResource.getDetails().entrySet()) {
            details.put(entry.getKey(), new PrayerDetails(
                    prayerId,
                    entry.getKey(),
                    entry.getValue().getTitle(),
                    entry.getValue().getContent(),
                    0L
            ));
        }

        Prayer newPrayer = new Prayer(
                prayerId,
                details,
                0L
        );

        return prayerToResourceConverter.convert(prayerRepository.saveAndFlush(newPrayer));
    }

}
