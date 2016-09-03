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
import pl.kielce.tu.worldyouthday.phone.resource.PhoneResource;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneResource;
import pl.kielce.tu.worldyouthday.phone.validator.UpdatePhoneValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdatePhoneService {

    @Autowired
    protected PhoneRepository phoneRepository;

    @Autowired
    protected PhoneResourceConverter phoneResourceConverter;

    @Autowired
    protected UpdatePhoneValidator updatePhoneValidator;

    @Autowired
    protected CityRepository cityRepository;


    @Transactional
    @Versionable(table = VersionTable.PHONES)
    public PhoneResource updatePhone(String id, UpdatePhoneResource phoneResource) throws ValidationException {
        updatePhoneValidator.validate(id, phoneResource);

        Map<Language, PhoneDetails> phoneDetails = new HashMap<>();

        for (Map.Entry<Language, UpdatePhoneDetailsResource> e : phoneResource.getDetails().entrySet()) {
            phoneDetails.put(e.getKey(), new PhoneDetails(e.getValue().getVersion(), new PhoneDetailsPK(id, e.getKey()), e.getValue().getOwner(), e.getValue().getDescription()));
        }

        Phone phone = new Phone(
                phoneResource.getVersion(),
                id,
                phoneResource.getNumber(),
                phoneDetails,
                cityRepository.findByIdIn(phoneResource.getCityIds())
        );

        return phoneResourceConverter.convert(phoneRepository.saveAndFlush(phone));
    }


}
