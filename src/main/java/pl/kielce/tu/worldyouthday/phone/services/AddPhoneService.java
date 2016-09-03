package pl.kielce.tu.worldyouthday.phone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.phone.Phone;
import pl.kielce.tu.worldyouthday.phone.PhoneDetails;
import pl.kielce.tu.worldyouthday.phone.PhoneDetailsPK;
import pl.kielce.tu.worldyouthday.phone.PhoneRepository;
import pl.kielce.tu.worldyouthday.phone.converter.PhoneResourceConverter;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneResource;
import pl.kielce.tu.worldyouthday.phone.resource.PhoneResource;
import pl.kielce.tu.worldyouthday.phone.validator.NewPhoneValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AddPhoneService {

    @Autowired
    protected PhoneRepository phoneRepository;

    @Autowired
    protected PhoneResourceConverter phoneResourceConverter;

    @Autowired
    protected NewPhoneValidator newPhoneValidator;

    @Autowired
    protected CityRepository cityRepository;

    @Transactional
    @Versionable(table = VersionTable.PHONES)
    public PhoneResource addPhone(NewPhoneResource phoneResource) throws ValidationException {

        newPhoneValidator.validate(phoneResource);

        String id = UUID.randomUUID().toString();

        Map<Language, PhoneDetails> phoneDetails = new HashMap<>();

        for (Map.Entry<Language, NewPhoneDetailsResource> e : phoneResource.getDetails().entrySet()) {
            phoneDetails.put(e.getKey(), new PhoneDetails(0L, new PhoneDetailsPK(id, e.getKey()), e.getValue().getOwner(), e.getValue().getDescription()));
        }

        Phone phone = new Phone(
                0L,
                id,
                phoneResource.getNumber(),
                phoneDetails,
                cityRepository.findByIdIn(phoneResource.getCityIds())
        );

        return phoneResourceConverter.convert(phoneRepository.saveAndFlush(phone));
    }

}
