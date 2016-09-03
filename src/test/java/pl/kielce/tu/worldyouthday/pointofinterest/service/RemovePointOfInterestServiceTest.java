package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class RemovePointOfInterestServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private RemovePointOfInterestService removePointOfInterestService;

    @Autowired
    private FindPointOfInterestService findPointOfInterestService;

    @Test
    public void shouldRemovePointOfInterest() throws ValidationException {
        String idToRemove = "454f41d3-10e0-441f-a856-e63b8d3caa81";
        removePointOfInterestService.removePointOfInterest(idToRemove);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages(idToRemove);

        Assert.assertEquals(null, result);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemovePointOfInterestBecauseNewsRefersToThePointOfInterest() throws Exception {
        String prayerId = "b9288672-14d9-42cc-a855-f4f9f4d75ce2";
        removePointOfInterestService.removePointOfInterest(prayerId);
    }


    @Test(expected = ValidationException.class)
    public void shouldNotRemovePointOfInterestWithoutValidId() throws ValidationException {
        removePointOfInterestService.removePointOfInterest("BLEEE");
    }
}
