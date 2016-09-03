package pl.kielce.tu.worldyouthday.prayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.Prayer;
import pl.kielce.tu.worldyouthday.prayer.PrayerDetails;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.prayer.converters.PrayerToResourceConverter;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerResource;
import pl.kielce.tu.worldyouthday.prayer.validator.UpdatePrayerValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdatePrayerService {

    @Autowired
    private PrayerRepository prayerRepository;

    @Autowired
    private PrayerToResourceConverter prayerToResourceConverter;

    @Autowired
    private UpdatePrayerValidator updatePrayerValidator;

    @Transactional
    @Versionable(table = VersionTable.PRAYERS)
    public PrayerResource updatePrayer(String id, UpdatePrayerResource updatePrayerResource) throws ValidationException {
        updatePrayerValidator.validate(id, updatePrayerResource);

        Map<Language, PrayerDetails> details = new HashMap<>();

        for (HashMap.Entry<Language, UpdatePrayerDetailsResource> entry : updatePrayerResource.getDetails().entrySet()) {
            details.put(entry.getKey(), new PrayerDetails(
                    id,
                    entry.getKey(),
                    entry.getValue().getTitle(),
                    entry.getValue().getContent(),
                    entry.getValue().getVersion()
            ));
        }

        Prayer updatePrayer = new Prayer(
                id,
                details,
                updatePrayerResource.getVersion()
        );

        return prayerToResourceConverter.convert(prayerRepository.saveAndFlush(updatePrayer));
    }
}
