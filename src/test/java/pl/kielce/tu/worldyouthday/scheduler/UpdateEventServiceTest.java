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
import pl.kielce.tu.worldyouthday.scheduler.resources.*;
import pl.kielce.tu.worldyouthday.scheduler.services.UpdateEventService;
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
public class UpdateEventServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UpdateEventService updateEventService;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String EVENT_ID = "7839890b-af6a-49b0-86cd-54a5c1b0c532";
    private static final String EVENT_START_DATE = "2016-08-07 10:10:00";
    private static final String EVENT_END_DATE = "2016-08-07 10:40:00";
    private static final String EVENT_TITLE = "TestEventTitle1";
    private static final String EVENT_DESCRIPTION = "TestEventDescription1";
    private static final String EVENT_CITY_ID = "ede9c925-e95a-448c-b5df-f2cca37cfce1";
    private static final String EVENT_LOCATION_ID = "454f41d3-10e0-441f-a856-e63b8d3caa81";

    private UpdateEventResource getCorrectEvent() throws ParseException {
        return UpdateEventResource.newBuilder()
                .withId(EVENT_ID)
                .withVersion(0L)
                .withCityId(EVENT_CITY_ID)
                .withLocationId(EVENT_LOCATION_ID)
                .withStartDate(formatter.parse(EVENT_START_DATE))
                .withEndDate(formatter.parse(EVENT_END_DATE))
                .withDetails(Language.ENGLISH, UpdateEventDetailsResource.newBuilder()
                        .withVersion(0L)
                        .withTitle(EVENT_TITLE)
                        .withDescription(EVENT_DESCRIPTION)
                        .build())
                .build();
    }

    private UpdateEventResource getEventWithSetField(String fieldName, Object fieldValue) throws IllegalAccessException, NoSuchFieldException, ParseException {
        UpdateEventResource updateEventResource = getCorrectEvent();

        Field numberField = updateEventResource.getClass().getDeclaredField(fieldName);
        numberField.setAccessible(true);
        numberField.set(updateEventResource, fieldValue);
        numberField.setAccessible(false);

        return updateEventResource;
    }

    private UpdateEventResource getEventWithNullField(String fieldName) throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithSetField(fieldName, null);
    }

    private UpdateEventResource getEventWithoutCity() throws ParseException, NoSuchFieldException, IllegalAccessException {
        return getEventWithNullField("cityId");
    }

    private UpdateEventResource getEventWithoutStartDate() throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithNullField("startDate");
    }

    private UpdateEventResource getEventWithoutEndDate() throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithNullField("endDate");
    }

    private UpdateEventResource getEventWithoutDefaultLanguageDetails() throws ParseException, NoSuchFieldException, IllegalAccessException {
        UpdateEventResource updateEventResource = getCorrectEvent();
        updateEventResource.getDetails().clear();

        return updateEventResource;
    }

    private UpdateEventResource getEventWithWrongCity() throws IllegalAccessException, NoSuchFieldException, ParseException {
        return getEventWithSetField("cityId", "nonexistentCityId");
    }

    private UpdateEventResource getEventWithWrongLocation() throws IllegalAccessException, NoSuchFieldException, ParseException {
        List<String> locationIds = new ArrayList<>();
        locationIds.add("nonexistentLocationId");

        return getEventWithSetField("locationsId", locationIds);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldUpdateEvent() throws Exception {
        EventResource updatedEvent = updateEventService.updateEvent(getCorrectEvent());

        assertEquals(updatedEvent.getId(), EVENT_ID);
        assertEquals(updatedEvent.getDetails().get(Language.ENGLISH).getTitle(), EVENT_TITLE);
        assertEquals(updatedEvent.getDetails().get(Language.ENGLISH).getDescription(), EVENT_DESCRIPTION);
        assertEquals(updatedEvent.getStartDate(), formatter.parse(EVENT_START_DATE));
        assertEquals(updatedEvent.getEndDate(), formatter.parse(EVENT_END_DATE));
        assertEquals(updatedEvent.getCityId(), EVENT_CITY_ID);
        assertTrue(updatedEvent.getLocationsId().contains(EVENT_LOCATION_ID));
    }

    @Test
    public void shouldThrowExceptionWithoutCity() throws ParseException, IllegalAccessException, NoSuchFieldException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid city id");

        updateEventService.updateEvent(getEventWithoutCity());
    }

    @Test
    public void shouldThrowExceptionWithoutStartDate() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid start date");

        updateEventService.updateEvent(getEventWithoutStartDate());
    }

    @Test
    public void shouldThrowExceptionWithoutEndDate() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid end date");

        updateEventService.updateEvent(getEventWithoutEndDate());
    }

    @Test
    public void shouldThrowExceptionWithoutDetailsInDefaultLanguage() throws ParseException, IllegalAccessException, NoSuchFieldException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Details not present in default language");

        updateEventService.updateEvent(getEventWithoutDefaultLanguageDetails());
    }

    @Test
    public void shouldThrowExceptionWithNonexistentCityId() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("City with id nonexistentCityId not exist");

        updateEventService.updateEvent(getEventWithWrongCity());
    }

    @Test
    public void shouldThrowExceptionWithNonexistentLocationId() throws IllegalAccessException, NoSuchFieldException, ParseException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Location with id nonexistentLocationId not exist");

        updateEventService.updateEvent(getEventWithWrongLocation());
    }

}
