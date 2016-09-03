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
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestSearchCriteria;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FindPointOfInterestServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private FindPointOfInterestService findPointOfInterestService;

    @Test
    public void shouldFindPointOfInterestByCity() {
        PointOfInterestSearchCriteria criteria = PointOfInterestSearchCriteria.newBuilder()
                .withLanguage(Language.ENGLISH)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .build();

        List<PointOfInterestResource> results = findPointOfInterestService.findAllPointsOfInterest(criteria);

        List<Language> resultLanguages = new ArrayList<>();
        List<String> resultNames = new ArrayList<>();
        List<String> resultDescriptions = new ArrayList<>();
        for (PointOfInterestResource r : results) {
            for (Map.Entry<Language, PointOfInterestDetailsResource> e : r.getDetails().entrySet()) {
                resultLanguages.add(e.getKey());
                resultNames.add(e.getValue().getName());
                resultDescriptions.add(e.getValue().getDescription());
            }
        }

        Assert.assertEquals(2, results.size());
        Assert.assertEquals(1, results.get(0).getDetails().size());
        Assert.assertEquals(1, results.get(1).getDetails().size());
        Assert.assertTrue(resultLanguages.contains(Language.ENGLISH));
        Assert.assertTrue(resultNames.contains("Castle in Checiny"));
        Assert.assertTrue(resultNames.contains("Kamien przy drodze EN"));
        Assert.assertTrue(resultDescriptions.contains("Wonderful castle!"));
        Assert.assertTrue(resultDescriptions.contains("Pikny kamien! EN"));
    }

    @Test
    public void shouldFindPointOfInterestByCityInDefaultLanguage() {
        PointOfInterestSearchCriteria criteria = PointOfInterestSearchCriteria.newBuilder()
                .withLanguage(Language.PORTUGUESE)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .build();

        List<PointOfInterestResource> results = findPointOfInterestService.findAllPointsOfInterest(criteria);

        List<Language> resultLanguages = new ArrayList<>();
        List<String> resultNames = new ArrayList<>();
        List<String> resultDescriptions = new ArrayList<>();
        for (PointOfInterestResource r : results) {
            for (Map.Entry<Language, PointOfInterestDetailsResource> e : r.getDetails().entrySet()) {
                resultLanguages.add(e.getKey());
                resultNames.add(e.getValue().getName());
                resultDescriptions.add(e.getValue().getDescription());
            }
        }

        Assert.assertEquals(2, results.size());
        Assert.assertTrue(resultLanguages.contains(Language.getDefault()));
        Assert.assertTrue(resultLanguages.contains(Language.PORTUGUESE));
        Assert.assertTrue(resultNames.contains("Castle in Checiny"));
        Assert.assertTrue(resultNames.contains("Kamien przy drodze PT"));
        Assert.assertTrue(resultDescriptions.contains("Wonderful castle!"));
        Assert.assertTrue(resultDescriptions.contains("Pikny kamien! PT"));
    }

    @Test
    public void shouldFindPointOfInterestByCityAndCategory() {
        PointOfInterestSearchCriteria criteria = PointOfInterestSearchCriteria.newBuilder()
                .withLanguage(Language.ENGLISH)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withCategoryId("f05fed75-d541-4d11-be21-40218ea32766")
                .build();

        List<PointOfInterestResource> results = findPointOfInterestService.findAllPointsOfInterest(criteria);

        List<String> resultNames = new ArrayList<>();
        List<String> resultDescriptions = new ArrayList<>();
        for (PointOfInterestResource r : results) {
            for (Map.Entry<Language, PointOfInterestDetailsResource> e : r.getDetails().entrySet()) {
                resultNames.add(e.getValue().getName());
                resultDescriptions.add(e.getValue().getDescription());
            }
        }

        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).getDetails().containsKey(Language.ENGLISH));
        Assert.assertTrue(resultNames.contains("Kamien przy drodze EN"));
        Assert.assertTrue(resultDescriptions.contains("Pikny kamien! EN"));
        Assert.assertEquals("f05fed75-d541-4d11-be21-40218ea32766", results.get(0).getCategoryId());
    }

    @Test
    public void shouldFindPointOfInterest() {
        PointOfInterestResource result = findPointOfInterestService.findPointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", Language.POLISH);

        Assert.assertEquals("Zamek w Checinach", result.getDetails().get(Language.POLISH).getName());
        Assert.assertEquals("Pikny zamek, ze hej!", result.getDetails().get(Language.POLISH).getDescription());
        Assert.assertEquals("4154d988-03bd-11e6-b512-3e1d05defe78", result.getCityId());
        Assert.assertEquals("36a63514-1f9c-4266-8ab0-763cc31dae92", result.getCategoryId());
        Assert.assertTrue(result.getDetails().containsKey(Language.POLISH));
        Assert.assertEquals(1, result.getDetails().size());
    }

    @Test
    public void shouldFindPointOfInterestInDefaultLanguage() {
        PointOfInterestResource result = findPointOfInterestService.findPointOfInterest("b9288672-14d9-42cc-a855-f4f9f4d75ce2", Language.ITALIAN);

        Assert.assertEquals("Castle in Checiny", result.getDetails().get(Language.getDefault()).getName());
        Assert.assertEquals("Wonderful castle!", result.getDetails().get(Language.getDefault()).getDescription());
        Assert.assertEquals("4154d988-03bd-11e6-b512-3e1d05defe78", result.getCityId());
        Assert.assertEquals("36a63514-1f9c-4266-8ab0-763cc31dae92", result.getCategoryId());
        Assert.assertTrue(result.getDetails().containsKey(Language.getDefault()));
        Assert.assertEquals(1, result.getDetails().size());
    }

    @Test
    public void shouldFindPointOfInterestInAllLanguages() {
        PointOfInterestResource result = findPointOfInterestService.findPointOfInterestInAllLanguages("b9288672-14d9-42cc-a855-f4f9f4d75ce2");

        Assert.assertEquals(3, result.getDetails().size());
        Assert.assertEquals("Zamek w Checinach", result.getDetails().get(Language.POLISH).getName());
        Assert.assertEquals("Pikny zamek, ze hej!", result.getDetails().get(Language.POLISH).getDescription());
        Assert.assertEquals("Castle in Checiny", result.getDetails().get(Language.ENGLISH).getName());
        Assert.assertEquals("Wonderful castle!", result.getDetails().get(Language.ENGLISH).getDescription());
    }
}
