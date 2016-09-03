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
import pl.kielce.tu.worldyouthday.scheduler.resources.UpdateEventDetailsResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.UpdateEventResource;
import pl.kielce.tu.worldyouthday.scheduler.validator.UpdateEventValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateEventService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private EventToResourceConverter eventToResourceConverter;

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private UpdateEventValidator updateEventValidator;
    @Autowired
    private CityRepository cityRepository;

    @Transactional
    @Versionable(table = VersionTable.SCHEDULER)
    public EventResource updateEvent(UpdateEventResource updateEventResource) throws ValidationException {
        updateEventValidator.validate(updateEventResource);

        Map<Language, EventDetails> details = new HashMap<>();

        for (HashMap.Entry<Language, UpdateEventDetailsResource> entry : updateEventResource.getDetails().entrySet()) {
            details.put(entry.getKey(), new EventDetails(
                    updateEventResource.getId(),
                    entry.getKey(),
                    entry.getValue().getTitle(),
                    entry.getValue().getDescription(),
                    entry.getValue().getVersion()
            ));
        }

        City city = cityRepository.findOne(updateEventResource.getCityId());

        List<PointOfInterest> pointOfInterests = new ArrayList<>();

        for (String locationId : updateEventResource.getLocationsId()) {
            pointOfInterests.add(pointOfInterestRepository.findOne(locationId));
        }
        Event updatePrayer = new Event(
                updateEventResource.getId(),
                pointOfInterests,
                updateEventResource.getStartDate(),
                updateEventResource.getEndDate(),
                details,
                city,
                updateEventResource.getVersion()
        );

        return eventToResourceConverter.convert(schedulerRepository.saveAndFlush(updatePrayer));
    }
}
