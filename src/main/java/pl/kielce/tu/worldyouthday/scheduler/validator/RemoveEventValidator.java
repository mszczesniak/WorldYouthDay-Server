package pl.kielce.tu.worldyouthday.scheduler.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.news.NewsDetailsRepository;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class RemoveEventValidator implements Validator<String> {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private NewsDetailsRepository newsDetailsRepository;

    @Override
    public void validate(String id) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (!schedulerRepository.exists(id)) {
            throw new ValidationException(String.format("Event with id %s not exist", id));
        }

        if (!newsDetailsRepository.findByTextLike("%<event%id=\"" + id + "\">%").isEmpty()) {
            throw new ValidationException("Remove news with refers to the event");
        }
    }
}
