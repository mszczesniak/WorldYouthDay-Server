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
import pl.kielce.tu.worldyouthday.scheduler.services.RemoveEventService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class RemoveEventServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private RemoveEventService removeEventService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldRemoveEvent() throws Exception {
        String prayerId = "1d996c1c-0d76-11e6-a148-3e1d05defe78";
        removeEventService.removeEvent(prayerId);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemoveEventBecauseNewsRefersToTheEvent() throws Exception {
        String prayerId = "7839890b-af6a-49b0-86cd-54a5c1b0c532";
        removeEventService.removeEvent(prayerId);
    }

    private static final String NONEXISTENT_EVENT_ID = "nonexistentEventId";

    @Test
    public void shouldThrowExceptionWithNullId() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid id");

        removeEventService.removeEvent(null);
    }

    @Test
    public void shouldThrowExceptionWithEmptyId() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Invalid id");

        removeEventService.removeEvent("");
    }

    @Test
    public void shouldThrowExceptionForNonexistentEvent() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Event with id nonexistentEventId not exist");

        removeEventService.removeEvent(NONEXISTENT_EVENT_ID);
    }
}
