package pl.kielce.tu.worldyouthday.scheduler.converters;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.scheduler.Event;
import pl.kielce.tu.worldyouthday.scheduler.EventDetails;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventDetailsResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToLanguageResourceConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventToResourceConverter extends ToLanguageResourceConverter<Event, EventResource, EventDetailsResource> {

    @Override
    public EventResource convert(Event event) {

        Map<Language, EventDetailsResource> map = new HashMap<>();

        for (HashMap.Entry<Language, EventDetails> entry : event.getDetails().entrySet()) {
            map.put(entry.getKey(), convertDetails(event, entry.getKey()));
        }

        return EventResource
                .newBuilder()
                .withId(event.getId())
                .withDetails(map)
                .withLocationsId(event.getLocations().stream().map(var -> var.getId()).collect(Collectors.toList()))
                .withStartDate(event.getStartDate())
                .withEndDate(event.getEndDate())
                .withCityId(event.getCity().getId())
                .withVersion(event.getVersion())
                .build();
    }

    @Override
    public EventResource convert(Event event, Language language) {

        if (language == null) {
            return convert(event);
        }

        return EventResource
                .newBuilder()
                .withId(event.getId())
                .withDetails(
                        event.getDetails(language).getId().getLanguage(),
                        convertDetails(event, language)
                )
                .withLocationsId(event.getLocations().stream().map(var -> var.getId()).collect(Collectors.toList()))
                .withStartDate(event.getStartDate())
                .withEndDate(event.getEndDate())
                .withCityId(event.getCity().getId())
                .withVersion(event.getVersion())
                .build();
    }

    @Override
    public EventDetailsResource convertDetails(Event event, Language language) {
        return EventDetailsResource
                .newBuilder()
                .withTitle(event.getDetails(language).getTitle())
                .withDescription(event.getDetails(language).getDescription())
                .withVersion(event.getDetails(language).getVersion())
                .build();
    }
}
