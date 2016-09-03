package pl.kielce.tu.worldyouthday.utils.tag.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class PrayerTagValidatorTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private PrayerTagValidator prayerTagValidator;

    @Test
    public void shouldPassValidation() throws ValidationException {
        String text = "Oto jest <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</poi>";

        prayerTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutId() throws ValidationException {
        String text = "Oto jest <prayer>modlitwa</poi>";

        prayerTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutValidId() throws ValidationException {
        String text = "Oto jest <prayer id='BLEE'>modlitwa</poi>";

        prayerTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithEmptyId() throws ValidationException {
        String text = "Oto jest <prayer id=''>modlitwa</poi>";

        prayerTagValidator.validate(text);
    }
}
