package pl.kielce.tu.worldyouthday.cities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.cities.validator.RemoveCityValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

@Service
public class RemoveCityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RemoveCityValidator removeCityValidator;


    @Versionable(table = VersionTable.CITIES)
    public void removeCity(String id) throws ValidationException {
        removeCityValidator.validate(id);
        cityRepository.delete(cityRepository.findOne(id));
    }

}