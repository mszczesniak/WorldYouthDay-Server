package pl.kielce.tu.worldyouthday.cities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.cities.converters.CityToResourceConverter;
import pl.kielce.tu.worldyouthday.cities.resources.CityResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

@Service
public class FindCityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityToResourceConverter cityToResourceConverter;

    public List<CityResource> getCityList() {
        return cityToResourceConverter.convert(cityRepository.findAll());
    }

    public CityResource getCityById(String id) throws ValidationException {

        City city = cityRepository.findOne(id);
        return city != null ? cityToResourceConverter.convert(city) : null;
    }

}