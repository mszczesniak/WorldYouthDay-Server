package pl.kielce.tu.worldyouthday.phone.converter;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.phone.Phone;
import pl.kielce.tu.worldyouthday.phone.PhoneDetails;
import pl.kielce.tu.worldyouthday.phone.resource.PhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.PhoneResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToLanguageResourceConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PhoneResourceConverter extends ToLanguageResourceConverter<Phone, PhoneResource, PhoneDetailsResource> {
    @Override
    public PhoneResource convert(Phone phone) {

        Map<Language, PhoneDetailsResource> map = new HashMap<>();

        for (HashMap.Entry<Language, PhoneDetails> entry : phone.getDetails().entrySet()) {
            map.put(entry.getKey(), convertDetails(phone, entry.getKey()));
        }

        return PhoneResource.newBuilder()
                .withId(phone.getId())
                .withNumber(phone.getNumber())
                .withDetails(map)
                .withCities(phone.getCities().stream().map(City::getId).collect(Collectors.toList()))
                .withVersion(phone.getVersion())
                .build();
    }

    @Override
    public PhoneResource convert(Phone phone, Language language) {

        if (language == null) {
            return convert(phone);
        }

        return PhoneResource.newBuilder()
                .withId(phone.getId())
                .withNumber(phone.getNumber())
                .withDetail(
                        phone.getDetails(language).getId().getLanguage(),
                        convertDetails(phone, language)
                )
                .withCities(phone.getCities().stream().map(City::getId).collect(Collectors.toList()))
                .withVersion(phone.getVersion())
                .build();
    }

    @Override
    public PhoneDetailsResource convertDetails(Phone phone, Language language) {
        return PhoneDetailsResource.newBuilder()
                .withOwner(phone.getDetails().get(language).getOwner())
                .withDescription(phone.getDetails().get(language).getDescription())
                .withVersion(phone.getDetails().get(language).getVersion())
                .build();
    }
}
