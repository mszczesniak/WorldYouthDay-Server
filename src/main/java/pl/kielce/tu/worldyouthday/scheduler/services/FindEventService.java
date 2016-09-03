package pl.kielce.tu.worldyouthday.scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.scheduler.Event;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerRepository;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerSearchCriteria;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerSpecifications;
import pl.kielce.tu.worldyouthday.scheduler.converters.EventToResourceConverter;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventResource;
import pl.kielce.tu.worldyouthday.scheduler.validator.FindEventValidator;
import pl.kielce.tu.worldyouthday.scheduler.validator.FindEventsValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;
import java.util.Optional;

@Service
public class FindEventService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private EventToResourceConverter eventToResourceConverter;

    @Autowired
    private SchedulerSpecifications schedulerSpecifications;

    @Autowired
    private FindEventsValidator findEventsValidator;

    @Autowired
    private FindEventValidator findEventValidator;

    public List<EventResource> getEvents(SchedulerSearchCriteria searchCriteria) throws ValidationException {

        findEventsValidator.validate(searchCriteria);

        Specification<Event> specification = Specifications.where(
                schedulerSpecifications.hasCity(searchCriteria.getCityId()))
                .and(schedulerSpecifications.dateFrom(searchCriteria.getDateFrom()))
                .and(schedulerSpecifications.dateTo(searchCriteria.getDateTo())
                );

        Optional<Language> language = searchCriteria.getLanguage();
        List<Event> events = schedulerRepository.findAll(specification);

        if (language.isPresent()) {
            return eventToResourceConverter.convert(events, language.get());
        } else {
            return eventToResourceConverter.convert(events);
        }

    }

    public EventResource getEventForId(String id) throws ValidationException {
        findEventValidator.validate(id);
        Event event = schedulerRepository.findOne(id);
        return event != null ? eventToResourceConverter.convert(event) : null;
    }
}
