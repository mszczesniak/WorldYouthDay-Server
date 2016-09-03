package pl.kielce.tu.worldyouthday.phone;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneResource;
import pl.kielce.tu.worldyouthday.phone.resource.PhoneResource;
import pl.kielce.tu.worldyouthday.phone.services.AddPhoneService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class AddPhoneServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private NewPhoneResource getCorrectNewPhoneResource(List<String> cityIds) {
        return NewPhoneResource
                .newBuilder()
                .withDetail(
                        Language.ENGLISH,
                        NewPhoneDetailsResource.newBuilder()
                                .withOwner("Owner en")
                                .withDescription("Description en")
                        .build()
                )
                .withDetail(
                        Language.POLISH,
                        NewPhoneDetailsResource.newBuilder()
                                .withOwner("Wlasciciel pl")
                                .withDescription("Opis pl")
                        .build()
                )
                .withNumber("111111111")
                .withCities(cityIds)
                .build();
    }

    private NewPhoneResource getCorrectIndependentNewPhoneResource() {
        return getCorrectNewPhoneResource(new ArrayList<>());
    }

    private NewPhoneResource getCorrectDependentNewPhoneResource(List<String> cityIds) {
        return getCorrectNewPhoneResource(cityIds);
    }

    private NewPhoneResource getNewPhoneResourceWithoutOwner(Language language) throws NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResource = getCorrectIndependentNewPhoneResource();

        NewPhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field ownerField = detail.getClass().getDeclaredField("owner");
        ownerField.setAccessible(true);
        ownerField.set(detail, null);
        ownerField.setAccessible(false);

        return phoneResource;
    }

    private NewPhoneResource getNewPhoneResourceWithoutDescription(Language language) throws NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResource = getCorrectIndependentNewPhoneResource();

        NewPhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field descriptionField = detail.getClass().getDeclaredField("description");
        descriptionField.setAccessible(true);
        descriptionField.set(detail, null);
        descriptionField.setAccessible(false);

        return phoneResource;
    }

    private NewPhoneResource getNewPhoneResourceWithEmptyOwner(Language language) throws NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResource = getCorrectIndependentNewPhoneResource();

        NewPhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field ownerField = detail.getClass().getDeclaredField("owner");
        ownerField.setAccessible(true);
        ownerField.set(detail, "");
        ownerField.setAccessible(false);

        return phoneResource;
    }

    private NewPhoneResource getNewPhoneResourceWithEmptyDescription(Language language) throws NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResource = getCorrectIndependentNewPhoneResource();

        NewPhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field descriptionField = detail.getClass().getDeclaredField("description");
        descriptionField.setAccessible(true);
        descriptionField.set(detail, "");
        descriptionField.setAccessible(false);

        return phoneResource;
    }

    private NewPhoneResource getNewWrongPhoneResourceWithoutNumber() throws NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResource = getCorrectIndependentNewPhoneResource();

        Field numberField = phoneResource.getClass().getDeclaredField("number");
        numberField.setAccessible(true);
        numberField.set(phoneResource, null);
        numberField.setAccessible(false);

        return phoneResource;
    }

    private NewPhoneResource getNewWrongPhoneResourceWithEmptyNumber() throws NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResource = getCorrectIndependentNewPhoneResource();

        Field numberField = phoneResource.getClass().getDeclaredField("number");
        numberField.setAccessible(true);
        numberField.set(phoneResource, "");
        numberField.setAccessible(false);

        return phoneResource;
    }

    private NewPhoneResource getWrongNewPhoneResourceWithoutEnglishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithoutOwner(Language.ENGLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithoutPolishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithoutOwner(Language.POLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithEmptyEnglishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithEmptyOwner(Language.ENGLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithEmptyPolishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithEmptyOwner(Language.POLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithoutEnglishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithoutDescription(Language.ENGLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithoutPolishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithoutDescription(Language.POLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithEmptyEnglishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithEmptyDescription(Language.ENGLISH);
    }

    private NewPhoneResource getWrongNewPhoneResourceWithEmptyPolishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getNewPhoneResourceWithEmptyDescription(Language.POLISH);
    }


    @Autowired
    private AddPhoneService phoneService;

    private static final String FIRST_CITY_ID = "ede9c925-e95a-448c-b5df-f2cca37cfce1";
    private static final String SECOND_CITY_ID = "c182d79c-5bc2-409b-b135-ea4af26608f4";


    @Test
    public void shouldAddIndependentPhone() throws ValidationException {
        NewPhoneResource phoneResourceExtended = getCorrectIndependentNewPhoneResource();

        PhoneResource addedPhone = phoneService.addPhone(phoneResourceExtended);

        assertTrue(addedPhone.getId() != null && !"".equals(addedPhone.getId()));
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getDescription(), addedPhone.getDetails().get(Language.ENGLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getOwner(), addedPhone.getDetails().get(Language.ENGLISH).getOwner());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getDescription(), addedPhone.getDetails().get(Language.POLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getOwner(), addedPhone.getDetails().get(Language.POLISH).getOwner());
        assertEquals(phoneResourceExtended.getNumber(), addedPhone.getNumber());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = ValidationException.class)
    public void shouldThrowNullOwnerExceptionWithoutPolishOwner() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithoutPolishOwner();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowNullOwnerExceptionWithoutEnglishOwner() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithoutEnglishOwner();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyOwnerExceptionWithoutPolishOwner() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty owner");

        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithEmptyPolishOwner();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyOwnerExceptionWithoutEnglishOwner() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty owner");

        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithEmptyEnglishOwner();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowNullDescriptionExceptionWithoutPolishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithoutPolishDescription();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowNullDescriptionExceptionWithoutEnglishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithoutEnglishDescription();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test
    public void shouldThrowNullDescriptionExceptionWithEmptyPolishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty description");

        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithEmptyPolishDescription();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test
    public void shouldThrowNullDescriptionExceptionWithEmptyEnglishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty description");

        NewPhoneResource phoneResourceExtended = getWrongNewPhoneResourceWithEmptyEnglishDescription();
        phoneService.addPhone(phoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyNumberExceptionWithoutNumber() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty phone number");

        NewPhoneResource newPhoneResource = getNewWrongPhoneResourceWithoutNumber();
        phoneService.addPhone(newPhoneResource);
    }

    @Test
    public void shouldThrowEmptyNumberExceptionWithEmptyNumber() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty phone number");

        NewPhoneResource newPhoneResource = getNewWrongPhoneResourceWithEmptyNumber();
        phoneService.addPhone(newPhoneResource);
    }

    @Test
    public void shouldAddDependentPhoneWithOneCity() throws ValidationException {
        NewPhoneResource phoneResourceExtended = getCorrectDependentNewPhoneResource(Collections.singletonList("4154d988-03bd-11e6-b512-3e1d05defe78"));
        PhoneResource addedPhone = phoneService.addPhone(phoneResourceExtended);

        assertTrue(addedPhone.getId() != null && !"".equals(addedPhone.getId()));
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getDescription(), addedPhone.getDetails().get(Language.ENGLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getOwner(), addedPhone.getDetails().get(Language.ENGLISH).getOwner());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getDescription(), addedPhone.getDetails().get(Language.POLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getOwner(), addedPhone.getDetails().get(Language.POLISH).getOwner());
        assertEquals(phoneResourceExtended.getNumber(), addedPhone.getNumber());
        assertEquals(phoneResourceExtended.getCityIds().get(0), "4154d988-03bd-11e6-b512-3e1d05defe78");
    }

    @Test
    public void shouldAddDependentPhoneWithManyCities() throws ValidationException {
        NewPhoneResource phoneResourceExtended = getCorrectDependentNewPhoneResource(Arrays.asList(FIRST_CITY_ID, SECOND_CITY_ID));
        PhoneResource addedPhone = phoneService.addPhone(phoneResourceExtended);

        assertTrue(addedPhone.getId() != null && !"".equals(addedPhone.getId()));
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getDescription(), addedPhone.getDetails().get(Language.ENGLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getOwner(), addedPhone.getDetails().get(Language.ENGLISH).getOwner());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getDescription(), addedPhone.getDetails().get(Language.POLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getOwner(), addedPhone.getDetails().get(Language.POLISH).getOwner());
        assertEquals(phoneResourceExtended.getNumber(), addedPhone.getNumber());
        assertEquals(phoneResourceExtended.getCityIds().size(), 2);
        assertTrue(phoneResourceExtended.getCityIds().stream().allMatch(cityId -> cityId.equals(FIRST_CITY_ID) || cityId.equals(SECOND_CITY_ID)));
    }
}
