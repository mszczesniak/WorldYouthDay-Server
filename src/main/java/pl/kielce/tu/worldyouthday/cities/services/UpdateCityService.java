package pl.kielce.tu.worldyouthday.cities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.cities.converters.CityToResourceConverter;
import pl.kielce.tu.worldyouthday.cities.resources.CityResource;
import pl.kielce.tu.worldyouthday.cities.resources.UpdateCityResource;
import pl.kielce.tu.worldyouthday.cities.validator.UpdateCityValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

@Service
public class UpdateCityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityToResourceConverter cityToResourceConverter;

    @Autowired
    private UpdateCityValidator updateCityValidator;


    @Transactional
    @Versionable(table = VersionTable.CITIES)
    public CityResource updateCity(String id, UpdateCityResource cityResource) throws ValidationException {

        updateCityValidator.validate(id, cityResource);

        City city = new City(
                id,
                cityResource.getName(),
                cityResource.getVersion()
        );
        return cityToResourceConverter.convert(cityRepository.saveAndFlush(city));
    }

}