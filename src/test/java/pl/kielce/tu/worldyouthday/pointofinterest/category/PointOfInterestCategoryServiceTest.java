package pl.kielce.tu.worldyouthday.pointofinterest.category;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.NewPointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.UpdatePointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 15.04.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class PointOfInterestCategoryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private PointOfInterestCategoryService pointOfInterestCategoryService;

    @Test
    public void shouldAddPointOfInterestCategory() throws ValidationException {
        NewPointOfInterestCategoryResource newPointOfInterestCategoryResource =
                NewPointOfInterestCategoryResource.newBuilder()
                        .withDetails(
                                Language.ENGLISH,
                                PointOfInterestCategoryDetailsResource
                                        .newBuilder()
                                        .withName("Castle")
                                        .build())
                        .withDetails(
                                Language.POLISH,
                                PointOfInterestCategoryDetailsResource
                                        .newBuilder()
                                        .withName("Zamek")
                                        .build())
                        .build();

        PointOfInterestCategoryResource categoryResource = pointOfInterestCategoryService.addPointOfInterestCategory(newPointOfInterestCategoryResource);

        PointOfInterestCategoryResource results = pointOfInterestCategoryService.findById(categoryResource.getId());
        Assert.assertEquals(2, results.getDetails().size());
        Assert.assertEquals("Zamek", results.getDetails().get(Language.POLISH).getName());
        Assert.assertEquals("Castle", results.getDetails().get(Language.ENGLISH).getName());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddPointOfInterestCategoryWithoutDefaultLanguage() throws ValidationException {
        NewPointOfInterestCategoryResource newPointOfInterestCategoryResource =
                NewPointOfInterestCategoryResource.newBuilder()
                        .withDetails(
                                Language.GERMAN,
                                PointOfInterestCategoryDetailsResource
                                        .newBuilder()
                                        .withName("Die Castle")
                                        .build())
                        .withDetails(
                                Language.POLISH,
                                PointOfInterestCategoryDetailsResource
                                        .newBuilder()
                                        .withName("Zamek")
                                        .build())
                        .build();

        pointOfInterestCategoryService.addPointOfInterestCategory(newPointOfInterestCategoryResource);
    }

    @Test
    public void shouldUpdatePointOfInterestCategory() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        UpdatePointOfInterestCategoryResource updateResource = UpdatePointOfInterestCategoryResource.newBuilder()
                .withDetails(
                        Language.ENGLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Castle mod")
                                .build())
                .withDetails(
                        Language.POLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Zamek mod")
                                .build())
                .withDetails(
                        Language.FRENCH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Le zamek")
                                .build())
                .withVersion(0L)
                .build();

        pointOfInterestCategoryService.updatePointOfInterestCategory(categoryId, updateResource);

        PointOfInterestCategoryResource results = pointOfInterestCategoryService.findById(categoryId);
        Assert.assertEquals(3, results.getDetails().size());
        Assert.assertEquals("Zamek mod", results.getDetails().get(Language.POLISH).getName());
        Assert.assertEquals("Castle mod", results.getDetails().get(Language.ENGLISH).getName());
        Assert.assertEquals("Le zamek", results.getDetails().get(Language.FRENCH).getName());
        Assert.assertFalse(results.getDetails().containsKey(Language.GERMAN));
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void shouldNotUpdatePointOfInterestCategoryWithoutValidVersion() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        UpdatePointOfInterestCategoryResource updateResource = UpdatePointOfInterestCategoryResource.newBuilder()
                .withDetails(
                        Language.ENGLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Castle mod")
                                .build())
                .withDetails(
                        Language.POLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Zamek mod")
                                .build())
                .withDetails(
                        Language.FRENCH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Le zamek")
                                .build())
                .withVersion(23L)
                .build();

        pointOfInterestCategoryService.updatePointOfInterestCategory(categoryId, updateResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestCategoryWithoutVersion() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        UpdatePointOfInterestCategoryResource updateResource = UpdatePointOfInterestCategoryResource.newBuilder()
                .withDetails(
                        Language.ENGLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Castle mod")
                                .build())
                .withDetails(
                        Language.POLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Zamek mod")
                                .build())
                .withDetails(
                        Language.FRENCH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Le zamek")
                                .build())
                .build();

        pointOfInterestCategoryService.updatePointOfInterestCategory(categoryId, updateResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestCategoryWithoutDefaultLanguage() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        UpdatePointOfInterestCategoryResource updateResource = UpdatePointOfInterestCategoryResource.newBuilder()
                .withDetails(
                        Language.GERMAN,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Castle mod")
                                .build())
                .withDetails(
                        Language.POLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Zamek mod")
                                .build())
                .withDetails(
                        Language.FRENCH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Le zamek")
                                .build())
                .withVersion(0L)
                .build();

        pointOfInterestCategoryService.updatePointOfInterestCategory(categoryId, updateResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdatePointOfInterestCategoryWithWrongId() throws ValidationException {
        String categoryId = "BLEEE";

        UpdatePointOfInterestCategoryResource updateResource = UpdatePointOfInterestCategoryResource.newBuilder()
                .withDetails(
                        Language.ENGLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Castle mod")
                                .build())
                .withDetails(
                        Language.POLISH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Zamek mod")
                                .build())
                .withDetails(
                        Language.FRENCH,
                        PointOfInterestCategoryDetailsResource
                                .newBuilder()
                                .withName("Le zamek")
                                .build())
                .withVersion(0L)
                .build();

        pointOfInterestCategoryService.updatePointOfInterestCategory(categoryId, updateResource);
    }

    @Test
    public void shouldRemovePointOfInterestCategory() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        pointOfInterestCategoryService.removePointOfInterestCategory(categoryId);

        PointOfInterestCategoryResource results = pointOfInterestCategoryService.findById(categoryId);
        Assert.assertEquals(null, results);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotRemovePointOfInterestCategoryWithWrongId() throws ValidationException {
        String categoryId = "BLEEE";

        pointOfInterestCategoryService.removePointOfInterestCategory(categoryId);
    }

    @Test
    public void shouldFindPointOfInterestCategory() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        PointOfInterestCategoryResource result = pointOfInterestCategoryService.findById(categoryId);

        Assert.assertEquals(categoryId, result.getId());
        Assert.assertEquals("Die Zamek", result.getDetails().get(Language.GERMAN).getName());
        Assert.assertTrue(result.getDetails().containsKey(Language.GERMAN));
        Assert.assertEquals(0L, result.getVersion().longValue());
    }

    @Test
    public void shouldFindAllPointOfInterestCategories() throws ValidationException {
        List<PointOfInterestCategoryResource> results = pointOfInterestCategoryService.findAllPointOfInterestCategories(Language.ENGLISH);

        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.get(0).getDetails().containsKey(Language.ENGLISH));
        Assert.assertTrue(results.get(1).getDetails().containsKey(Language.ENGLISH));
    }

    @Test
    public void shouldFindAllPointOfInterestCategoriesInDefaultLanguage() throws ValidationException {
        List<PointOfInterestCategoryResource> results = pointOfInterestCategoryService.findAllPointOfInterestCategories(Language.FRENCH);

        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.get(0).getDetails().containsKey(Language.getDefault()));
        Assert.assertTrue(results.get(1).getDetails().containsKey(Language.getDefault()));
    }

    @Test
    public void shouldFindPointOfInterestCategoryInAllLanguages() throws ValidationException {
        String categoryId = "36a63514-1f9c-4266-8ab0-763cc31dae92";

        PointOfInterestCategoryResource results = pointOfInterestCategoryService.findById(categoryId);

        Assert.assertEquals(3, results.getDetails().size());
        Assert.assertTrue(results.getDetails().containsKey(Language.ENGLISH));
        Assert.assertTrue(results.getDetails().containsKey(Language.POLISH));
        Assert.assertTrue(results.getDetails().containsKey(Language.GERMAN));
        Assert.assertEquals("Castle", results.getDetails().get(Language.ENGLISH).getName());
        Assert.assertEquals("Zamek", results.getDetails().get(Language.POLISH).getName());
        Assert.assertEquals("Die Zamek", results.getDetails().get(Language.GERMAN).getName());
    }
}
