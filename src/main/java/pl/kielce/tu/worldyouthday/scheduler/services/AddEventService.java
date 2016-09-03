package pl.kielce.tu.worldyouthday.scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.scheduler.Event;
import pl.kielce.tu.worldyouthday.scheduler.EventDetails;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerRepository;
import pl.kielce.tu.worldyouthday.scheduler.converters.EventToResourceConverter;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventDetailsResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventResource;
import pl.kielce.tu.worldyouthday.scheduler.validator.NewEventValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.*;

@Service
public class AddEventService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private EventToResourceConverter eventToResourceConverter;

    @Autowired
    private NewEventValidator newEventValidator;

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private CityRepository cityRepository;


    @Transactional
    @Versionable(table = VersionTable.SCHEDULER)
    public EventResource addEvent(NewEventResource newEventResource) throws ValidationException {

        newEventValidator.validate(newEventResource);

        Map<Language, EventDetails> details = new HashMap<>();
        String eventId = UUID.randomUUID().toString();

        for (HashMap.Entry<Language, NewEventDetailsResource> entry : newEventResource.getDetails().entrySet()) {
            details.put(entry.getKey(), new EventDetails(
                    eventId,
                    entry.getKey(),
                    entry.getValue().getTitle(),
                    entry.getValue().getDescription(),
                    0L
            ));
        }

        City city = cityRepository.findOne(newEventResource.getCityId());

        List<PointOfInterest> pointOfInterests = new ArrayList<>();

        for (String locationId : newEventResource.getLocationsId()) {
            pointOfInterests.add(pointOfInterestRepository.findOne(locationId));
        }

        Event newEvent = new Event(
                eventId,
                pointOfInterests,
                newEventResource.getStartDate(),
                newEventResource.getEndDate(),
                details,
                city,
                0L
        );

        return eventToResourceConverter.convert(schedulerRepository.saveAndFlush(newEvent));
    }


}
