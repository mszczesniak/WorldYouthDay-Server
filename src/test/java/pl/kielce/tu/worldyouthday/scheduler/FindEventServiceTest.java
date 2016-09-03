package pl.kielce.tu.worldyouthday.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventDetailsResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.EventResource;
import pl.kielce.tu.worldyouthday.scheduler.services.FindEventService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FindEventServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String FIRST_EVENT_ID = "1d996c1c-0d76-11e6-a148-3e1d05defe78";
    private static final String SECOND_EVENT_ID = "2192092e-3953-11e6-ac61-9e71128cae77";
    private static final String THIRD_EVENT_ID = "7839890b-af6a-49b0-86cd-54a5c1b0c532";

    private static final String FIRST_CITY_ID = "4154d988-03bd-11e6-b512-3e1d05defe78";
    private static final String SECOND_CITY_ID = "4e87eacb-dd20-4eba-aeef-d9dc5f3e5507";

    private static final String FIRST_LOCATION_ID = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";
    private static final String SECOND_LOCATION_ID = "454f41d3-10e0-441f-a856-e63b8d3caa81";


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private FindEventService findEventService;

    private void validateFirst(EventResource resource) throws Exception {
        assertEquals(resource.getId(), FIRST_EVENT_ID);
        assertEquals(resource.getVersion(), new Long(0L));
        assertEquals(resource.getCityId(), FIRST_CITY_ID);
        assertTrue(resource.getLocationsId().contains(FIRST_LOCATION_ID));
        assertTrue(formatter.format(resource.getStartDate()).equals("2016-08-07 10:10:00"));
        assertTrue(formatter.format(resource.getEndDate()).equals("2016-08-07 10:40:00"));
        assertEquals(resource.getDetails().entrySet().size(), 2);

        EventDetailsResource polishDetails = resource.getDetails().get(Language.POLISH);
        assertEquals(polishDetails.getVersion(), new Long(0L));
        assertEquals(polishDetails.getTitle(), "Koncert");
        assertEquals(polishDetails.getDescription(), "Koncert zespolu combo lombo");

        EventDetailsResource englishDetails = resource.getDetails().get(Language.ENGLISH);
        assertEquals(englishDetails.getVersion(), new Long(0L));
        assertEquals(englishDetails.getTitle(), "Koncert EN");
        assertEquals(englishDetails.getDescription(), "Koncert zespolu combo lombo EN");
    }

    private void validateFirstInPolish(EventResource resource) throws Exception {
        assertEquals(resource.getId(), FIRST_EVENT_ID);
        assertEquals(resource.getVersion(), new Long(0L));
        assertEquals(resource.getCityId(), FIRST_CITY_ID);
        assertTrue(resource.getLocationsId().contains(FIRST_LOCATION_ID));
        assertTrue(formatter.format(resource.getStartDate()).equals("2016-08-07 10:10:00"));
        assertTrue(formatter.format(resource.getEndDate()).equals("2016-08-07 10:40:00"));
        assertEquals(resource.getDetails().entrySet().size(), 1);

        EventDetailsResource polishDetails = resource.getDetails().get(Language.POLISH);
        assertEquals(polishDetails.getVersion(), new Long(0L));
        assertEquals(polishDetails.getTitle(), "Koncert");
        assertEquals(polishDetails.getDescription(), "Koncert zespolu combo lombo");

    }

    private void validateSecond(EventResource resource) throws Exception {
        assertEquals(resource.getId(), SECOND_EVENT_ID);
        assertEquals(resource.getVersion(), new Long(0L));
        assertEquals(resource.getCityId(), SECOND_CITY_ID);
        assertTrue(resource.getLocationsId().contains(SECOND_LOCATION_ID));
        assertTrue(formatter.format(resource.getStartDate()).equals("2016-08-07 21:12:00"));
        assertTrue(formatter.format(resource.getEndDate()).equals("2016-08-07 23:12:00"));
        assertEquals(resource.getDetails().entrySet().size(), 2);

        EventDetailsResource polishDetails = resource.getDetails().get(Language.POLISH);
        assertEquals(polishDetails.getVersion(), new Long(0L));
        assertEquals(polishDetails.getTitle(), "TytulEventa1");
        assertEquals(polishDetails.getDescription(), "TrescEvent1");

        EventDetailsResource englishDetails = resource.getDetails().get(Language.ENGLISH);
        assertEquals(englishDetails.getVersion(), new Long(0L));
        assertEquals(englishDetails.getTitle(), "TitleEvent1");
        assertEquals(englishDetails.getDescription(), "DetailsEvent1");
    }

    private void validateSecondInPolish(EventResource resource) throws Exception {
        assertEquals(resource.getId(), SECOND_EVENT_ID);
        assertEquals(resource.getVersion(), new Long(0L));
        assertEquals(resource.getCityId(), SECOND_CITY_ID);
        assertTrue(resource.getLocationsId().contains(SECOND_LOCATION_ID));
        assertTrue(formatter.format(resource.getStartDate()).equals("2016-08-07 21:12:00"));
        assertTrue(formatter.format(resource.getEndDate()).equals("2016-08-07 23:12:00"));
        assertEquals(resource.getDetails().entrySet().size(), 1);

        EventDetailsResource polishDetails = resource.getDetails().get(Language.POLISH);
        assertEquals(polishDetails.getVersion(), new Long(0L));
        assertEquals(polishDetails.getTitle(), "TytulEventa1");
        assertEquals(polishDetails.getDescription(), "TrescEvent1");

    }

    private void validateThird(EventResource resource) throws Exception {
        assertEquals(resource.getId(), THIRD_EVENT_ID);
        assertEquals(resource.getVersion(), new Long(0L));
        assertEquals(resource.getCityId(), FIRST_CITY_ID);
        assertTrue(resource.getLocationsId().contains(FIRST_LOCATION_ID));
        assertTrue(resource.getLocationsId().contains(SECOND_LOCATION_ID));
        assertTrue(formatter.format(resource.getStartDate()).equals("2016-06-07 22:12:00"));
        assertTrue(formatter.format(resource.getEndDate()).equals("2016-08-05 11:12:00"));
        assertEquals(resource.getDetails().entrySet().size(), 2);

        EventDetailsResource polishDetails = resource.getDetails().get(Language.POLISH);
        assertEquals(polishDetails.getVersion(), new Long(0L));
        assertEquals(polishDetails.getTitle(), "TytulEventa2");
        assertEquals(polishDetails.getDescription(), "TrescEvent2");

        EventDetailsResource englishDetails = resource.getDetails().get(Language.ENGLISH);
        assertEquals(englishDetails.getVersion(), new Long(0L));
        assertEquals(englishDetails.getTitle(), "TitleEvent2");
        assertEquals(englishDetails.getDescription(), "DetailsEvent2");
    }

    private void validateThirdInPolish(EventResource resource) throws Exception {
        assertEquals(resource.getId(), THIRD_EVENT_ID);
        assertEquals(resource.getVersion(), new Long(0L));
        assertEquals(resource.getCityId(), FIRST_CITY_ID);
        assertTrue(resource.getLocationsId().contains(FIRST_LOCATION_ID));
        assertTrue(resource.getLocationsId().contains(SECOND_LOCATION_ID));
        assertTrue(formatter.format(resource.getStartDate()).equals("2016-06-07 22:12:00"));
        assertTrue(formatter.format(resource.getEndDate()).equals("2016-08-05 11:12:00"));
        assertEquals(resource.getDetails().entrySet().size(), 1);

        EventDetailsResource polishDetails = resource.getDetails().get(Language.POLISH);
        assertEquals(polishDetails.getVersion(), new Long(0L));
        assertEquals(polishDetails.getTitle(), "TytulEventa2");
        assertEquals(polishDetails.getDescription(), "TrescEvent2");

    }

    @Test
    public void shouldFindEventsWithEmptySearchCriteria() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .build());

        assertEquals(eventList.size(), 3);
        validateFirst(eventList.get(0));
        validateSecond(eventList.get(1));
        validateThird(eventList.get(2));

    }

    @Test
    public void shouldFindEventsWithNullSearchCriteria() throws Exception {
        findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withCityId(null)
                        .withLanguage(null)
                        .withDateFrom(null)
                        .withDateTo(null)
                        .build());

    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionBecauseFindEventsWithEmptyCityId() throws Exception {
        findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withCityId("")
                        .withLanguage(null)
                        .withDateFrom(null)
                        .withDateTo(null)
                        .build());

    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionBecauseFindEventsWithWrongCityId() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withCityId("Bleee")
                        .withLanguage(null)
                        .withDateFrom(null)
                        .withDateTo(null)
                        .build());

        assertEquals(eventList.size(), 3);
        validateFirst(eventList.get(0));
        validateSecond(eventList.get(1));
        validateThird(eventList.get(2));

    }

    @Test
    public void shouldFindEventsByCity() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withCityId(FIRST_CITY_ID)
                        .build());

        assertEquals(eventList.size(), 2);
        validateFirst(eventList.get(0));
        validateThird(eventList.get(1));

    }

    @Test
    public void shouldFindEventsByLanguage() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withLanguage(Language.POLISH)
                        .build());

        assertEquals(eventList.size(), 3);
        validateFirstInPolish(eventList.get(0));
        validateSecondInPolish(eventList.get(1));
        validateThirdInPolish(eventList.get(2));

    }

    @Test
    public void shouldFindEventsByDateFrom() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withDateFrom(formatter.parse("2016-08-07 21:11:00"))
                        .build());

        assertEquals(eventList.size(), 1);
        validateSecond(eventList.get(0));
    }

    @Test
    public void shouldFindEventsByDateTo() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withDateTo(formatter.parse("2016-08-05 11:15:00"))
                        .build());

        assertEquals(eventList.size(), 1);
        validateThird(eventList.get(0));
    }

    @Test
    public void shouldFindEmptyEventList() throws Exception {
        List<EventResource> eventList = findEventService.getEvents(
                SchedulerSearchCriteria.newBuilder()
                        .withDateFrom(formatter.parse("2011-08-05 11:15:00"))
                        .withDateTo(formatter.parse("2013-08-05 11:15:00"))
                        .build());

        assertTrue(eventList.isEmpty());
    }

    @Test
    public void shouldFindEventById() throws Exception {
        EventResource event = findEventService.getEventForId(FIRST_EVENT_ID);
        validateFirst(event);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindEventByIdBecauseIdIsNull() throws Exception {
        findEventService.getEventForId(null);
    }


    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindEventByIdBecauseIdIsEmpty() throws Exception {
        findEventService.getEventForId("");
    }


    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindEventByIdBecauseIdIsWrong() throws Exception {
        findEventService.getEventForId("bleee");
    }
}
