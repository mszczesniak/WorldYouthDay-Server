package pl.kielce.tu.worldyouthday.utils.tag.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 02.06.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class EventTagValidatorTest {
    @Autowired
    private EventTagValidator eventTagValidator;

    @Test
    public void shouldPassValidation() throws ValidationException {
        String text = "Oto jest <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event>";

        eventTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutId() throws ValidationException {
        String text = "Oto jest <event>zabytek</event>";

        eventTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutValidId() throws ValidationException {
        String text = "Oto jest <event id='BLEE'>zabytek</event>";

        eventTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithEmptyId() throws ValidationException {
        String text = "Oto jest <event id=''>zabytek</event>";

        eventTagValidator.validate(text);
    }
}
