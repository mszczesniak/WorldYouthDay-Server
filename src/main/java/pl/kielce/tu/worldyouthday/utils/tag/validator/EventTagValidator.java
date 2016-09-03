package pl.kielce.tu.worldyouthday.utils.tag.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.tag.EventTag;
import pl.kielce.tu.worldyouthday.utils.tag.parser.EventTagParser;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 02.06.2016.
 */

@Component
public class EventTagValidator implements TagValidator {
    @Autowired
    private EventTagParser eventTagParser;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Override
    public void validate(String text) throws ValidationException {
        try {
            List<EventTag> eventTags = eventTagParser.parse(text);

            for (EventTag e : eventTags) {
                checkTag(e);
            }
        } catch (NumberFormatException ex) {
            throw new ValidationException(ex);
        }
    }

    private void checkTag(EventTag tag) throws ValidationException {
        if (Strings.isNullOrEmpty(tag.getId())) {
            throw new ValidationException("Invalid id");
        }

        if (!schedulerRepository.exists(tag.getId())) {
            throw new ValidationException(String.format("Event with id: %s doesn't exist", tag.getId()));
        }
    }
}
