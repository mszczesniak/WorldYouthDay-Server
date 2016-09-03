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
import pl.kielce.tu.worldyouthday.phone.services.FindPhoneService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FindPhoneServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String FIRST_PHONE_ID = "7d535251-f713-4b9f-9283-c6d90ea9332c";
    private static final String SECOND_PHONE_ID = "09edbbb4-5a51-498d-9cab-b7f886581fff";
    private static final String THIRD_PHONE_ID = "599b0c2d-9e50-4c1e-8fe3-01ceb0f7c90f";
    private static final String FIRST_INDEPENDENT_PHONE_ID = "dae50ffd-21ce-44cd-8efa-0c5b8b3d164c";
    private static final String SECOND_INDEPENDENT_PHONE_ID = "99b20497-9594-4ea4-8c6a-ea99045cd6a4";

    private static final String FIRST_CITY_ID = "ede9c925-e95a-448c-b5df-f2cca37cfce1";
    private static final String SECOND_CITY_ID = "c182d79c-5bc2-409b-b135-ea4af26608f4";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private FindPhoneService phoneService;

    private void validateFirstExtended(PhoneResource phoneResource) {
        assertEquals(phoneResource.getId(), FIRST_PHONE_ID);
        assertEquals(phoneResource.getDetails().get(Language.POLISH).getDescription(), "Telefon do Kacpra");
        assertEquals(phoneResource.getDetails().get(Language.ENGLISH).getDescription(), "Phone to Kacper");
        assertEquals(phoneResource.getDetails().get(Language.POLISH).getOwner(), "Pan Kacper Bublik");
        assertEquals(phoneResource.getDetails().get(Language.ENGLISH).getOwner(), "Mr Kacper Bublik");
        assertEquals(phoneResource.getNumber(), "999444222");
        assertEquals(phoneResource.getCityIds().size(), 1);
    }

    @Test
    public void shouldFindOnePhoneExtended() throws ValidationException {
        PhoneResource phoneResource = phoneService.findById(FIRST_PHONE_ID);
        validateFirstExtended(phoneResource);
    }

    @Test
    public void shouldThrowEmptyIdExceptionWithEmptyIdForExtended() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty Id");

        phoneService.findById("");
    }

    @Test
    public void shouldThrowEmptyIdExceptionWithNullIdForExtended() throws ValidationException {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("Empty Id");

        phoneService.findById(null);
    }

    @Test
    public void shouldFindAllPhonesExtended() {
        List<PhoneResource> phonesExtended = phoneService.findAll(PhoneSearchCriteria.newBuilder().build());
        assertTrue(phonesExtended.stream().allMatch(a -> a.getId().equals(FIRST_PHONE_ID) || a.getId().equals(SECOND_PHONE_ID) || a.getId().equals(THIRD_PHONE_ID) || a.getId().equals(FIRST_INDEPENDENT_PHONE_ID) || a.getId().equals(SECOND_INDEPENDENT_PHONE_ID)));
    }

    @Test
    public void shouldFindFourPhonesTwoIndependentExtended() {
        List<PhoneResource> phonesExtended = phoneService.findAll(PhoneSearchCriteria.newBuilder().withCityId(SECOND_CITY_ID).build());
        assertTrue(phonesExtended.stream().allMatch(a -> a.getId().equals(FIRST_INDEPENDENT_PHONE_ID) || a.getId().equals(SECOND_INDEPENDENT_PHONE_ID) || a.getId().equals(SECOND_PHONE_ID) || a.getId().equals(THIRD_PHONE_ID)));
    }
}
