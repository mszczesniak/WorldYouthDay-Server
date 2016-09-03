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
import pl.kielce.tu.worldyouthday.phone.resource.PhoneResource;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneDetailsResource;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneResource;
import pl.kielce.tu.worldyouthday.phone.services.UpdatePhoneService;
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
public class UpdatePhoneServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static UpdatePhoneResource getCorrectUpdatePhoneResource(List<String> cityIds) {
        return UpdatePhoneResource
                .newBuilder()
                .withDetail(
                        Language.ENGLISH,
                        UpdatePhoneDetailsResource.newBuilder()
                                .withVersion(0L)
                                .withOwner("TestOwnerUpdatedEN")
                                .withDescription("TestDescriptionUpdatedEN")
                                .build()
                )
                .withDetail(
                        Language.POLISH,
                        UpdatePhoneDetailsResource.newBuilder()
                                .withVersion(0L)
                                .withOwner("TestOwnerUpdatedPL")
                                .withDescription("TestDescriptionUpdatedPL")
                                .build()
                )
                .withNumber("111222333")
                .withCities(cityIds)
                .withVersion(0L)
                .build();
    }

    protected static UpdatePhoneResource getCorrectIndependentUpdatePhoneResource() {
        return getCorrectUpdatePhoneResource(new ArrayList<>());
    }

    protected static UpdatePhoneResource getCorrectDependentUpdatePhoneResource(List<String> cityIds) {
        return getCorrectUpdatePhoneResource(cityIds);
    }

    protected static UpdatePhoneResource getUpdatePhoneResourceWithoutOwner(Language language) throws IllegalAccessException, NoSuchFieldException {
        UpdatePhoneResource phoneResource = getCorrectIndependentUpdatePhoneResource();

        UpdatePhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field ownerField = detail.getClass().getDeclaredField("owner");
        ownerField.setAccessible(true);
        ownerField.set(detail, null);
        ownerField.setAccessible(false);

        return phoneResource;
    }

    protected static UpdatePhoneResource getUpdatePhoneResourceWithoutDescription(Language language) throws NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResource = getCorrectIndependentUpdatePhoneResource();

        UpdatePhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field descriptionField = detail.getClass().getDeclaredField("description");
        descriptionField.setAccessible(true);
        descriptionField.set(detail, null);
        descriptionField.setAccessible(false);

        return phoneResource;
    }

    protected static UpdatePhoneResource getUpdatePhoneResourceWithEmptyOwner(Language language) throws NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResource = getCorrectIndependentUpdatePhoneResource();

        UpdatePhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field ownerField = detail.getClass().getDeclaredField("owner");
        ownerField.setAccessible(true);
        ownerField.set(detail, "");
        ownerField.setAccessible(false);

        return phoneResource;
    }

    protected static UpdatePhoneResource getUpdatePhoneResourceWithEmptyDescription(Language language) throws NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResource = getCorrectIndependentUpdatePhoneResource();

        UpdatePhoneDetailsResource detail = phoneResource.getDetails().get(language);
        Field descriptionField = detail.getClass().getDeclaredField("description");
        descriptionField.setAccessible(true);
        descriptionField.set(detail, "");
        descriptionField.setAccessible(false);

        return phoneResource;
    }

    protected static UpdatePhoneResource getUpdateWrongPhoneResourceWithoutNumber() throws NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResource = getCorrectIndependentUpdatePhoneResource();

        Field numberField = phoneResource.getClass().getDeclaredField("number");
        numberField.setAccessible(true);
        numberField.set(phoneResource, null);
        numberField.setAccessible(false);

        return phoneResource;
    }

    protected static UpdatePhoneResource getUpdateWrongPhoneResourceWithEmptyNumber() throws NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResource = getCorrectIndependentUpdatePhoneResource();

        Field numberField = phoneResource.getClass().getDeclaredField("number");
        numberField.setAccessible(true);
        numberField.set(phoneResource, "");
        numberField.setAccessible(false);

        return phoneResource;
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithoutEnglishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithoutOwner(Language.ENGLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithoutPolishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithoutOwner(Language.POLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithEmptyEnglishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithEmptyOwner(Language.ENGLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithEmptyPolishOwner() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithEmptyOwner(Language.POLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithoutEnglishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithoutDescription(Language.ENGLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithoutPolishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithoutDescription(Language.POLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithEmptyEnglishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithEmptyDescription(Language.ENGLISH);
    }

    protected static UpdatePhoneResource getWrongUpdatePhoneResourceWithEmptyPolishDescription() throws NoSuchFieldException, IllegalAccessException {
        return getUpdatePhoneResourceWithEmptyDescription(Language.POLISH);
    }

    @Autowired
    private UpdatePhoneService phoneService;

    private final String PHONE_ID = "99b20497-9594-4ea4-8c6a-ea99045cd6a4";

    private static final String FIRST_CITY_ID = "ede9c925-e95a-448c-b5df-f2cca37cfce1";
    private static final String SECOND_CITY_ID = "c182d79c-5bc2-409b-b135-ea4af26608f4";
    private static final String THIRD_CITY_ID = "61dcec9f-82bc-4489-abd7-6a1a25f8b7b3";

    @Test
    public void shouldUpdateCorrectIndependentPhone() throws ValidationException {
        UpdatePhoneResource phoneToUpdate = getCorrectIndependentUpdatePhoneResource();
        PhoneResource updatedPhone = phoneService.updatePhone(PHONE_ID, phoneToUpdate);

        assertTrue(updatedPhone.getId() != null && !"".equals(updatedPhone.getId()));
        assertEquals(updatedPhone.getId(), PHONE_ID);
        assertEquals(phoneToUpdate.getDetails().get(Language.ENGLISH).getDescription(), updatedPhone.getDetails().get(Language.ENGLISH).getDescription());
        assertEquals(phoneToUpdate.getDetails().get(Language.ENGLISH).getOwner(), updatedPhone.getDetails().get(Language.ENGLISH).getOwner());
        assertEquals(phoneToUpdate.getDetails().get(Language.POLISH).getDescription(), updatedPhone.getDetails().get(Language.POLISH).getDescription());
        assertEquals(phoneToUpdate.getDetails().get(Language.POLISH).getOwner(), updatedPhone.getDetails().get(Language.POLISH).getOwner());
        assertEquals(phoneToUpdate.getNumber(), updatedPhone.getNumber());
    }

    @Test
    public void shouldUpdateDependentPhoneWithOneCity() throws ValidationException {
        UpdatePhoneResource phoneResourceExtended = getCorrectDependentUpdatePhoneResource(Collections.singletonList(FIRST_CITY_ID));
        PhoneResource updatedPhone = phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
        System.out.println(updatedPhone);

        assertTrue(updatedPhone.getId() != null && !"".equals(updatedPhone.getId()));
        assertEquals(updatedPhone.getId(), PHONE_ID);
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getDescription(), updatedPhone.getDetails().get(Language.POLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getOwner(), updatedPhone.getDetails().get(Language.POLISH).getOwner());
        assertEquals(phoneResourceExtended.getNumber(), updatedPhone.getNumber());
        assertEquals(phoneResourceExtended.getCityIds().get(0), FIRST_CITY_ID);
    }

    @Test
    public void shouldUpdateDependentPhoneWithManyCities() throws ValidationException {
        UpdatePhoneResource phoneResourceExtended = getCorrectDependentUpdatePhoneResource(Arrays.asList(FIRST_CITY_ID, SECOND_CITY_ID, THIRD_CITY_ID));
        PhoneResource updatedPhone = phoneService.updatePhone(PHONE_ID, phoneResourceExtended);

        assertTrue(updatedPhone.getId() != null && !"".equals(updatedPhone.getId()));
        assertEquals(updatedPhone.getId(), PHONE_ID);
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getDescription(), updatedPhone.getDetails().get(Language.ENGLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.ENGLISH).getOwner(), updatedPhone.getDetails().get(Language.ENGLISH).getOwner());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getDescription(), updatedPhone.getDetails().get(Language.POLISH).getDescription());
        assertEquals(phoneResourceExtended.getDetails().get(Language.POLISH).getOwner(), updatedPhone.getDetails().get(Language.POLISH).getOwner());
        assertEquals(phoneResourceExtended.getNumber(), updatedPhone.getNumber());
        assertEquals(phoneResourceExtended.getCityIds().size(), 3);
        assertTrue(phoneResourceExtended.getCityIds().stream().allMatch(cityId-> cityId.equals(FIRST_CITY_ID) || cityId.equals(SECOND_CITY_ID) || cityId.equals(THIRD_CITY_ID)));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = ValidationException.class)
    public void shouldThrowNullOwnerExceptionWithoutPolishOwner() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithoutPolishOwner();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowNullOwnerExceptionWithoutEnglishOwner() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithoutEnglishOwner();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyOwnerExceptionWithoutPolishOwner() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty owner");

        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithEmptyPolishOwner();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyOwnerExceptionWithoutEnglishOwner() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty owner");

        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithEmptyEnglishOwner();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowNullDescriptionExceptionWithoutPolishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithoutPolishDescription();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowNullDescriptionExceptionWithoutEnglishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithoutEnglishDescription();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test
    public void shouldThrowNullDescriptionExceptionWithEmptyPolishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty description");

        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithEmptyPolishDescription();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test
    public void shouldThrowNullDescriptionExceptionWithEmptyEnglishDescription() throws ValidationException, NoSuchFieldException, IllegalAccessException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty description");

        UpdatePhoneResource phoneResourceExtended = getWrongUpdatePhoneResourceWithEmptyEnglishDescription();
        phoneService.updatePhone(PHONE_ID, phoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyNumberExceptionWithoutNumber() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty phone number");

        UpdatePhoneResource newPhoneResourceExtended = getUpdateWrongPhoneResourceWithoutNumber();
        phoneService.updatePhone(PHONE_ID, newPhoneResourceExtended);
    }

    @Test
    public void shouldThrowEmptyNumberExceptionWithEmptyNumber() throws NoSuchFieldException, IllegalAccessException, ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty phone number");

        UpdatePhoneResource newPhoneResourceExtended = getUpdateWrongPhoneResourceWithEmptyNumber();
        phoneService.updatePhone(PHONE_ID, newPhoneResourceExtended);
    }
}
