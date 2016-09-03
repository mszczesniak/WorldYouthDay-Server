package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.UpdatePointOfInterestResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class UpdatePointOfInterestServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UpdatePointOfInterestService updatePointOfInterestService;

    @Autowired
    private FindPointOfInterestService findPointOfInterestService;

    @Test
    public void shouldUpdatePointOfInterest() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.FRENCH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska FR")
                        .withDescription("ale szkoła FR")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        PointOfInterestResource poi = updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);

        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages(poi.getId());

        Assert.assertEquals(2, result.getDetails().size());
        Assert.assertEquals("Politecnika Swiętokrzyska FR", result.getDetails().get(Language.FRENCH).getName());
        Assert.assertEquals("ale szkoła FR", result.getDetails().get(Language.FRENCH).getDescription());
        Assert.assertEquals("Politecnika Swiętokrzyska University", result.getDetails().get(Language.ENGLISH).getName());
        Assert.assertEquals("So cool", result.getDetails().get(Language.ENGLISH).getDescription());
        Assert.assertFalse(result.getDetails().containsKey(Language.POLISH));
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutVersion() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.FRENCH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska FR")
                        .withDescription("ale szkoła FR")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldNotUpdatePointOfInterestWithoutValidVersion() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.FRENCH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska FR")
                        .withDescription("ale szkoła FR")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(34L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutValidId() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("BLEEE", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutValidCityId() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("BLEE")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutValidCategoryId() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("BLEE")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.ENGLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutDetailInDefaultLanguage() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutDetails() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutCityId() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutCategoryId() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutLongitude() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutLatitude() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutId() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska")
                        .withDescription("ale szkoła")
                        .build())
                .withDetails(Language.PORTUGUESE, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest(null, resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestWithoutDetailsInDefaultLanguage() throws ValidationException {
        UpdatePointOfInterestResource resource = UpdatePointOfInterestResource.newBuilder()
                .withLatitude(45.34D)
                .withLongitude(33.54D)
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.FRENCH, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska FR")
                        .withDescription("ale szkoła FR")
                        .build())
                .withDetails(Language.GERMAN, PointOfInterestDetailsResource.newBuilder()
                        .withName("Politecnika Swiętokrzyska University")
                        .withDescription("So cool")
                        .build())
                .withVersion(1L)
                .build();

        updatePointOfInterestService.updatePointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", resource);
    }
}
