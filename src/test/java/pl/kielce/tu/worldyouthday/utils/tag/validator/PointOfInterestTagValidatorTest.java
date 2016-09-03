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
public class PointOfInterestTagValidatorTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private PointOfInterestTagValidator pointOfInterestTagValidator;

    @Test
    public void shouldPassValidation() throws ValidationException {
        String text = "Oto jest <poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>zabytek</poi>";

        pointOfInterestTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutId() throws ValidationException {
        String text = "Oto jest <poi>zabytek</poi>";

        pointOfInterestTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithoutValidId() throws ValidationException {
        String text = "Oto jest <poi id='BLEE'>zabytek</poi>";

        pointOfInterestTagValidator.validate(text);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotPassValidationWithEmptyId() throws ValidationException {
        String text = "Oto jest <poi id=''>zabytek</poi>";

        pointOfInterestTagValidator.validate(text);
    }
}
