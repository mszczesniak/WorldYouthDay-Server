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
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.NewPointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class AddPointOfInterestServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AddPointOfInterestService addPointOfInterestService;

    @Autowired
    private FindPointOfInterestService findPointOfInterestService;

    @Test
    public void shouldAddPointOfInterest() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("36a63514-1f9c-4266-8ab0-763cc31dae92")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        PointOfInterestResource poi = addPointOfInterestService.addPointOfInterest(resource);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages(poi.getId());

        Assert.assertEquals(2, result.getDetails().size());
        Assert.assertEquals("Politecnika Swiętokrzyska", result.getDetails().get(Language.POLISH).getName());
        Assert.assertEquals("ale szkoła", result.getDetails().get(Language.POLISH).getDescription());
        Assert.assertEquals("Politecnika Swiętokrzyska University", result.getDetails().get(Language.ENGLISH).getName());
        Assert.assertEquals("So cool", result.getDetails().get(Language.ENGLISH).getDescription());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestWithoutDetailInDefaultLanguage() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("36a63514-1f9c-4266-8ab0-763cc31dae92")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        addPointOfInterestService.addPointOfInterest(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestWithoutDetails() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("36a63514-1f9c-4266-8ab0-763cc31dae92")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .build();

        addPointOfInterestService.addPointOfInterest(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestWithoutCityId() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("36a63514-1f9c-4266-8ab0-763cc31dae92")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        addPointOfInterestService.addPointOfInterest(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestWithoutCategoryId() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        addPointOfInterestService.addPointOfInterest(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestWithoutLongitude() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withCategoryId("36a63514-1f9c-4266-8ab0-763cc31dae92")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        addPointOfInterestService.addPointOfInterest(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestWithoutLatitude() throws ValidationException {
        NewPointOfInterestResource resource = NewPointOfInterestResource.newBuilder()
                .withLongitude(33.54D)
                .withCategoryId("36a63514-1f9c-4266-8ab0-763cc31dae92")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        addPointOfInterestService.addPointOfInterest(resource);
    }
}
