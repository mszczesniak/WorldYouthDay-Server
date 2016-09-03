package pl.kielce.tu.worldyouthday.prayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.prayer.Prayer;
import pl.kielce.tu.worldyouthday.prayer.PrayerRepository;
import pl.kielce.tu.worldyouthday.prayer.validator.RemovePrayerValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

@Service
public class DeletePrayerService {

    @Autowired
    private PrayerRepository prayerRepository;

    @Autowired
    private RemovePrayerValidator removePrayerValidator;

    @Versionable(table = VersionTable.PRAYERS)
    public void removePrayer(String prayerId) throws ValidationException {
        removePrayerValidator.validate(prayerId);

        Prayer prayer = prayerRepository.findOne(prayerId);

        prayerRepository.delete(prayer);
    }


}
