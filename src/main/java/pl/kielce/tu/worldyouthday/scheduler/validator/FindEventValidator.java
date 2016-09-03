package pl.kielce.tu.worldyouthday.scheduler.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class FindEventValidator implements Validator<String> {

    @Autowired
    private SchedulerRepository schedulerRepository;


    @Override
    public void validate(String id) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid event id");
        }

        if (!schedulerRepository.exists(id)) {
            throw new ValidationException(String.format("Event with id %s not exist", id));

        }

    }
}
