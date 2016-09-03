package pl.kielce.tu.worldyouthday.cities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.cities.converters.CityToResourceConverter;
import pl.kielce.tu.worldyouthday.cities.resources.CityResource;
import pl.kielce.tu.worldyouthday.cities.resources.NewCityResource;
import pl.kielce.tu.worldyouthday.cities.validator.CreateCityValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.UUID;

@Service
public class AddCityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityToResourceConverter cityToResourceConverter;

    @Autowired
    private CreateCityValidator createCityValidator;

    @Transactional
    @Versionable(table = VersionTable.CITIES)
    public CityResource addCity(NewCityResource cityResource) throws ValidationException {

        createCityValidator.validate(cityResource);

        City city = new City(
                UUID.randomUUID().toString(),
                cityResource.getName(),
                0L
        );
        return cityToResourceConverter.convert(cityRepository.saveAndFlush(city));
    }

}