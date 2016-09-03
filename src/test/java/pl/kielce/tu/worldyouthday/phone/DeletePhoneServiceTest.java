package pl.kielce.tu.worldyouthday.phone;

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
import pl.kielce.tu.worldyouthday.phone.services.RemovePhoneService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class DeletePhoneServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static String PHONE_ID = "7d535251-f713-4b9f-9283-c6d90ea9332c";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private RemovePhoneService phoneService;

    @Test
    public void shouldDeletePhone() throws ValidationException {
        phoneService.removePhone(PHONE_ID);
    }

    @Test
    public void shouldThrowEmptyIdExceptionWithEmptyId() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty Id");
        phoneService.removePhone("");
    }

    @Test
    public void shouldThrowEmptyIdExceptionWithNullId() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty Id");
        phoneService.removePhone(null);
    }

    @Test
    public void shouldThrowPhoneNotFoundException() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Phone not found");
        phoneService.removePhone("Not present");
    }
}

