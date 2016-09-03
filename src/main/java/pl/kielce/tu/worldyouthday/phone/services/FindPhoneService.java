package pl.kielce.tu.worldyouthday.phone.services;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.phone.Phone;
import pl.kielce.tu.worldyouthday.phone.PhoneRepository;
import pl.kielce.tu.worldyouthday.phone.PhoneSearchCriteria;
import pl.kielce.tu.worldyouthday.phone.converter.PhoneResourceConverter;
import pl.kielce.tu.worldyouthday.phone.resource.PhoneResource;
import pl.kielce.tu.worldyouthday.phone.validator.FindPhoneValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindPhoneService {

    @Autowired
    protected PhoneRepository phoneRepository;

    @Autowired
    protected PhoneResourceConverter phoneResourceConverter;

    @Autowired
    protected FindPhoneValidator findPhoneValidator;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<PhoneResource> findAll(PhoneSearchCriteria searchCriteria) {
        String cityId = searchCriteria.getCityId().orElse("");

        return filterPhonesByCity(phoneRepository.findAll(), cityId).stream()
                .map(phone -> phoneResourceConverter.convert(phone))
                .collect(Collectors.toList());
    }

    private List<Phone> filterPhonesByCity(List<Phone> phones, String cityId) {
        return Strings.isNullOrEmpty(cityId) ? phones : phones.stream().filter(
                phone -> phone.getCities().size() == 0 ||
                        phone.getCities().stream().map(City::getId).
                                anyMatch(cityId::equals)).collect(Collectors.toList());

        //FIXME to powinno sie odbywac na bazie!
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public PhoneResource findById(String id) throws IllegalStateException, ValidationException {
        findPhoneValidator.validate(id);
        Phone phone = phoneRepository.findOne(id);
        return phone != null ? phoneResourceConverter.convert(phone) : null;
    }
}
