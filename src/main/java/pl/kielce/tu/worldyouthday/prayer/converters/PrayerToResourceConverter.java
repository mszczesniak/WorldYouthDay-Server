package pl.kielce.tu.worldyouthday.prayer.converters;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.Prayer;
import pl.kielce.tu.worldyouthday.prayer.PrayerDetails;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToLanguageResourceConverter;

import java.util.HashMap;
import java.util.Map;

@Component
public class PrayerToResourceConverter extends ToLanguageResourceConverter<Prayer, PrayerResource, PrayerDetailsResource> {

    @Override
    public PrayerResource convert(Prayer prayer) {

        Map<Language, PrayerDetailsResource> prayerDetailsMap = new HashMap<>();

        for (HashMap.Entry<Language, PrayerDetails> entry : prayer.getDetails().entrySet()) {
            prayerDetailsMap.put(entry.getKey(), convertDetails(prayer, entry.getKey()));
        }


        return PrayerResource
                .newBuilder()
                .withId(prayer.getId())
                .withDetails(prayerDetailsMap)
                .withVersion(prayer.getVersion())
                .build();
    }

    @Override
    public PrayerResource convert(Prayer prayer, Language language) {

        if (language == null) {
            return convert(prayer);
        }

        return PrayerResource
                .newBuilder()
                .withId(prayer.getId())
                .withDetail(
                        prayer.getDetails(language).getId().getLanguage(),
                        convertDetails(prayer, language)
                )
                .withVersion(prayer.getVersion())
                .build();
    }

    @Override
    public PrayerDetailsResource convertDetails(Prayer prayer, Language language) {
        return PrayerDetailsResource
                .newBuilder()
                .withTitle(prayer.getDetails(language).getTitle())
                .withContent(prayer.getDetails(language).getContent())
                .withVersion(prayer.getDetails(language).getVersion())
                .build();
    }

}
