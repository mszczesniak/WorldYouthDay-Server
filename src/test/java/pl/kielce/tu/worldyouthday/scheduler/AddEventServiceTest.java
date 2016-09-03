package pl.kielce.tu.worldyouthday.scheduler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventDetailsResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventResource;
import pl.kielce.tu.worldyouthday.scheduler.services.AddEventService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class AddEventServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AddEventService addEventService;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String EVENT_START_DATE = "2016-08-07 10:10:00";
    private static final String EVENT_END_DATE = "2016-08-07 10:40:00";
    private static final String EVENT_TITLE = "TestEventTitle1";
    private static final String EVENT_DESCRIPTION = "TestEventDescription1";
    private static final String EVENT_CITY_ID = "ede9c925-e95a-448c-b5df-f2cca37cfce1";
    private static final String EVENT_LOCATION_ID = "454f41d3-10e0-441f-a856-e63b8d3caa81";

    private NewEventResource getCorrectEvent() throws ParseException {
        return NewEventResource.newBuilder()
                .withCityId(EVENT_CITY_ID)
                .withLocationsId(EVENT_LOCATION_ID)
                .withStartDate(formatter.parse(EVENT_START_DATE))
                .withEndDate(formatter.parse(EVENT_END_DATE))
                .withDetails(Language.ENGLISH, NewEventDetailsResource.newBuilder()
                        .withTitle(EVENT_TITLE)
                        .withDescription(EVENT_DESCRIPTION)
                        .build())
                .build();
    }

    private NewEventResource getEventWithSetField(String fieldName, Object fieldValue) throws IllegalAccessException, NoSuchFieldException, ParseException {
        NewEventResource newEventResource = getCorrectEvent();

        Field numberField = newEventResource.getClass().getDeclaredField(fieldName);
        numberField.setAccessible(true);
        numberField.set(newEventResource, fieldValue);
        numberField.setAccessible(false);

        return newEventResource;
    }

    private NewEventResource getEventWithNullField(String fieldName) throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithSetField(fieldName, null);
    }

    private NewEventResource getEventWithoutCity() throws ParseException, NoSuchFieldException, IllegalAccessException {
        return getEventWithNullField("cityId");
    }

    private NewEventResource getEventWithoutStartDate() throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithNullField("startDate");
    }

    private NewEventResource getEventWithoutEndDate() throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithNullField("endDate");
    }

    private NewEventResource getEventWithoutDefaultLanguageDetails() throws ParseException, NoSuchFieldException, IllegalAccessException {
        NewEventResource newEventResource = getCorrectEvent();
        newEventResource.getDetails().clear();

        return newEventResource;
    }

    private NewEventResource getEventWithWrongCity() throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithSetField("cityId", "nonexistentCityId");
    }

    private NewEventResource getEventWithWrongLocation() throws IllegalAccessException, NoSuchFieldException, ParseException {
        List<String> locationIds = new ArrayList<>();
        locationIds.add("nonexistentLocationId");

        return getEventWithSetField("locationsId", locationIds);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldAddNewEvent() throws Exception {
        EventResource addedEvent = addEventService.addEvent(getCorrectEvent());

        assertEquals(addedEvent.getDetails().get(Language.ENGLISH).getTitle(), EVENT_TITLE);
        assertEquals(addedEvent.getDetails().get(Language.ENGLISH).getDescription(), EVENT_DESCRIPTION);
        assertEquals(addedEvent.getStartDate(), formatter.parse(EVENT_START_DATE));
        assertEquals(addedEvent.getEndDate(), formatter.parse(EVENT_END_DATE));
        assertEquals(addedEvent.getCityId(), EVENT_CITY_ID);
        assertTrue(addedEvent.getLocationsId().contains(EVENT_LOCATION_ID));
    }

    @Test
    public void shouldThrowExceptionWithoutCity() throws ParseException, IllegalAccessException, NoSuchFieldException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid city id");

        addEventService.addEvent(getEventWithoutCity());
    }

    @Test
    public void shouldThrowExceptionWithoutStartDate() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid start date");

        addEventService.addEvent(getEventWithoutStartDate());
    }

    @Test
    public void shouldThrowExceptionWithoutEndDate() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid end date");

        addEventService.addEvent(getEventWithoutEndDate());
    }

    @Test
    public void shouldThrowExceptionWithoutDetailsInDefaultLanguage() throws ParseException, IllegalAccessException, NoSuchFieldException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Details not present in default language");

        addEventService.addEvent(getEventWithoutDefaultLanguageDetails());
    }

    @Test
    public void shouldThrowExceptionWithNonexistentCityId() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("City with id nonexistentCityId not exist");

        addEventService.addEvent(getEventWithWrongCity());
    }

    @Test
    public void shouldThrowExceptionWithNonexistentLocationId() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Location with id nonexistentLocationId not exist");

        addEventService.addEvent(getEventWithWrongLocation());
    }
}
