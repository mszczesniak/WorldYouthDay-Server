package pl.kielce.tu.worldyouthday.city;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.cities.resources.CityResource;
import pl.kielce.tu.worldyouthday.cities.resources.NewCityResource;
import pl.kielce.tu.worldyouthday.cities.resources.UpdateCityResource;
import pl.kielce.tu.worldyouthday.cities.services.AddCityService;
import pl.kielce.tu.worldyouthday.cities.services.FindCityService;
import pl.kielce.tu.worldyouthday.cities.services.RemoveCityService;
import pl.kielce.tu.worldyouthday.cities.services.UpdateCityService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class CityServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FindCityService findCityService;
    @Autowired
    private AddCityService addCityService;
    @Autowired
    private UpdateCityService updateCityService;
    @Autowired
    private RemoveCityService removeCityService;

    @Test
    public void shouldFindDefaultCityKielce() throws Exception {
        List<CityResource> results = findCityService.getCityList();
        assertTrue(results.size() > 0);
    }

    @Test
    public void shouldCreateNewCity() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);
        assertFalse(findCityService.getCityList().isEmpty());
        assertFalse(cityResource.getId().isEmpty());
        assertEquals(cityResource.getName(), newCityResource.getName());
        assertEquals(new Long(cityResource.getVersion()), new Long(0L));
    }

    @Test
    public void shouldFindFourCities() throws Exception {

        NewCityResource newCityResource1 = NewCityResource
                .newBuilder()
                .withName("Checiny")
                .build();

        addCityService.addCity(newCityResource1);

        NewCityResource newCityResource2 = NewCityResource
                .newBuilder()
                .withName("Nowiny")
                .build();

        addCityService.addCity(newCityResource2);

        assertTrue(findCityService.getCityList().size() > 0);
    }

    @Test
    public void shouldFindById() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);
        assertNotNull(findCityService.getCityById(cityResource.getId()));
    }

    @Test
    public void shouldThrowValidationExceptionWhenFindByIdBecauseIdNotExist() throws Exception {
        assertNull(findCityService.getCityById("id"));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenCreateBecauseNameAlreadyExist() throws Exception {

        NewCityResource newCityResource1 = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();
        NewCityResource newCityResource2 = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        addCityService.addCity(newCityResource1);
        addCityService.addCity(newCityResource2);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionBecauseNameIsEmpty() throws Exception {

        NewCityResource newCityResource1 = NewCityResource
                .newBuilder()
                .withName("")
                .build();

        addCityService.addCity(newCityResource1);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenCreateBecauseNameIsNull() throws Exception {

        NewCityResource newCityResource1 = NewCityResource
                .newBuilder()
                .withName(null)
                .build();

        addCityService.addCity(newCityResource1);
    }

    @Test
    public void shouldUpdateCity() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName("newName")
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity(cityResource.getId(), updateCityResource);

        cityResource = findCityService.getCityById(cityResource.getId());

        assertFalse(findCityService.getCityList().isEmpty());
        assertFalse(cityResource.getId().isEmpty());
        assertEquals(cityResource.getName(), "newName");
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void shouldThrowObjectOptimisticLockingFailureException() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource1 = UpdateCityResource
                .newBuilder()
                .withName("newName1")
                .withVersion(cityResource.getVersion())
                .build();

        UpdateCityResource updateCityResource2 = UpdateCityResource
                .newBuilder()
                .withName("newName2")
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity(cityResource.getId(), updateCityResource1);
        updateCityService.updateCity(cityResource.getId(), updateCityResource2);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseNameAlreadyExist() throws Exception {

        NewCityResource newCityResource1 = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource1 = addCityService.addCity(newCityResource1);

        NewCityResource newCityResource2 = NewCityResource
                .newBuilder()
                .withName("test2")
                .build();

        CityResource cityResource2 = addCityService.addCity(newCityResource2);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName(cityResource2.getName())
                .withVersion(cityResource1.getVersion())
                .build();

        updateCityService.updateCity(cityResource1.getId(), updateCityResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseIdIsBad() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName("test")
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity("error", updateCityResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseIdIsEmpty() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName("test")
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity("", updateCityResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseIdIsNull() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName("test")
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity(null, updateCityResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseNameIsEmpty() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName("")
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity(cityResource.getId(), updateCityResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseNameIsNull() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName(null)
                .withVersion(cityResource.getVersion())
                .build();

        updateCityService.updateCity(cityResource.getId(), updateCityResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionWhenUpdateBecauseVersionIsNull() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test1")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);

        UpdateCityResource updateCityResource = UpdateCityResource
                .newBuilder()
                .withName("test")
                .withVersion(null)
                .build();

        updateCityService.updateCity(cityResource.getId(), updateCityResource);
    }

    @Test
    public void shouldRemoveCity() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);
        int sizeBeforeRemove = findCityService.getCityList().size();
        removeCityService.removeCity(cityResource.getId());
        int sizeAfterRemove = findCityService.getCityList().size();
        assertEquals(sizeAfterRemove, sizeBeforeRemove - 1);

    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemoveCityBecauseIdIsBad() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);
        removeCityService.removeCity("id");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemoveCityBecauseIdIsEmpty() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);
        removeCityService.removeCity("");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemoveCityBecauseIdIsNull() throws Exception {

        NewCityResource newCityResource = NewCityResource
                .newBuilder()
                .withName("test321")
                .build();

        CityResource cityResource = addCityService.addCity(newCityResource);
        removeCityService.removeCity(null);
    }

}
