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
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class LocationTagValidatorTest {
    @Autowired
    private LocationTagValidator locationTagValidator;

    @Test
    public void shouldPassValidation() throws ValidationException {
        String text = "Witymy w <location latitude='34.34' longitude='23.12'>naszym domu</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutLatitude() throws ValidationException {
        String text = "Witymy w <location longitude='23.12'>naszym domu</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutLongitude() throws ValidationException {
        String text = "Witymy w <location latitude='34.34'>naszym domu</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutLongitudeAndLatitude() throws ValidationException {
        String text = "Witymy w <location>naszym domu</location>";

        locationTagValidator.validate(text);
    }

    @Test
    public void shouldPassValidationMultiple() throws ValidationException {
        String text = "Witymy w <location latitude='34.34' longitude='23.12'>naszym domu</location> oraz w <location latitude='4.31' longitude='1.0'>piwnicy</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationMultipleWithoutLatitude() throws ValidationException {
        String text = "Witymy w <location latitude='34.34' longitude='23.12'>naszym domu</location> oraz w <location longitude='1.0'>piwnicy</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationMultipleWithoutLongitude() throws ValidationException {
        String text = "Witymy w <location latitude='34.34' longitude='23.12'>naszym domu</location> oraz w <location latitude='4.31'>piwnicy</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationMultipleWithoutLongitudeAndLatitude() throws ValidationException {
        String text = "Witymy w <location latitude='34.34' longitude='23.12'>naszym domu</location> oraz w <location>piwnicy</location>";

        locationTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutValidCoordinates() throws ValidationException {
        String text = "Witymy w <location latitude='dfas' longitude='3aa'>naszym domu</location>";

        locationTagValidator.validate(text);
    }
}
