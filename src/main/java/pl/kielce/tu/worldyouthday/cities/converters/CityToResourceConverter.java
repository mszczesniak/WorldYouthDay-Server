package pl.kielce.tu.worldyouthday.cities.converters;


import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.resources.CityResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToResourceConverter;

@Component
public class CityToResourceConverter extends ToResourceConverter<City, CityResource> {

    @Override
    public CityResource convert(City city) {
        return CityResource
                .newBuilder()
                .withId(city.getId())
                .withName(city.getName())
                .withVersion(city.getVersion())
                .build();
    }
}
