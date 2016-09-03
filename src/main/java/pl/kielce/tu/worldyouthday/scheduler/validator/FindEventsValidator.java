package pl.kielce.tu.worldyouthday.scheduler.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerSearchCriteria;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

import java.util.Optional;


@Component
public class FindEventsValidator implements Validator<SchedulerSearchCriteria> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(SchedulerSearchCriteria criteria) throws ValidationException {

        Optional<String> cityId = criteria.getCityId();
        if (cityId.isPresent() && !cityRepository.exists(cityId.get())) {
            throw new ValidationException("Invalid city id");
        }
    }
}
