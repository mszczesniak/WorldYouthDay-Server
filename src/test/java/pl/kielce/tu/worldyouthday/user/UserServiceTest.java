package pl.kielce.tu.worldyouthday.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.user.resources.NewUserResource;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.user.services.*;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ChangePasswordUserService changePasswordUserService;

    @Autowired
    private FindUserService findUserService;

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private RemoveUserService removeUserService;

    @Autowired
    private UpdateUserService updateUserService;

    @Test
    public void shouldFindAdminByLogin() throws Exception {
        UserResource admin = findUserService.getUserByLogin("admin");

        assertEquals(admin.getId(), "9ac1bf56-e499-11e5-9730-9a79f06e9478");
        assertEquals(admin.getLogin(), "admin");
        assertEquals(admin.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(admin.getRole(), Role.ADMIN);
        assertEquals(admin.getFirstName(), "Mateusz");
        assertEquals(admin.getLastName(), "Szczesniak");
        assertEquals(admin.getAddress(), "Skiby 113, 26-060 Checiny");
        assertEquals(admin.getEmail(), "mszczesniak16@gmail.com");
        assertNotNull(admin.getLastPasswordReset());
        assertEquals(admin.getVersion(), new Long(0L));
    }

    @Test
    public void shouldFindUserByLogin() throws Exception {
        UserResource admin = findUserService.getUserByLogin("user");

        assertEquals(admin.getId(), "177b9ad8-17b9-11e6-b6ba-3e1d05defe78");
        assertEquals(admin.getLogin(), "user");
        assertEquals(admin.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(admin.getRole(), Role.USER);
        assertEquals(admin.getFirstName(), "Mateusz");
        assertEquals(admin.getLastName(), "Szczesniak");
        assertEquals(admin.getAddress(), "Skiby 113, 26-060 Checiny");
        assertEquals(admin.getEmail(), "mszczesniak16spam@gmail.com");
        assertNotNull(admin.getLastPasswordReset());
        assertEquals(admin.getVersion(), new Long(0L));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindByLoginBecauseUserLoginNotExist() throws Exception {
        findUserService.getUserByLogin("nie ma takiego typa");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindByLoginBecauseUserLoginIsEmpty() throws Exception {
        findUserService.getUserByLogin("");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindByLoginBecauseUserLoginIsNull() throws Exception {
        findUserService.getUserByLogin(null);
    }


    @Test
    public void shouldFindAdminById() throws Exception {
        UserResource admin = findUserService.getUserById("9ac1bf56-e499-11e5-9730-9a79f06e9478");

        assertEquals(admin.getId(), "9ac1bf56-e499-11e5-9730-9a79f06e9478");
        assertEquals(admin.getLogin(), "admin");
        assertEquals(admin.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(admin.getRole(), Role.ADMIN);
        assertEquals(admin.getFirstName(), "Mateusz");
        assertEquals(admin.getLastName(), "Szczesniak");
        assertEquals(admin.getAddress(), "Skiby 113, 26-060 Checiny");
        assertEquals(admin.getEmail(), "mszczesniak16@gmail.com");
        assertNotNull(admin.getLastPasswordReset());
        assertEquals(admin.getVersion(), new Long(0L));
    }

    @Test
    public void shouldFindUserById() throws Exception {
        UserResource user = findUserService.getUserById("177b9ad8-17b9-11e6-b6ba-3e1d05defe78");

        assertEquals(user.getId(), "177b9ad8-17b9-11e6-b6ba-3e1d05defe78");
        assertEquals(user.getLogin(), "user");
        assertEquals(user.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(user.getRole(), Role.USER);
        assertEquals(user.getFirstName(), "Mateusz");
        assertEquals(user.getLastName(), "Szczesniak");
        assertEquals(user.getAddress(), "Skiby 113, 26-060 Checiny");
        assertEquals(user.getEmail(), "mszczesniak16spam@gmail.com");
        assertNotNull(user.getLastPasswordReset());
        assertEquals(user.getVersion(), new Long(0L));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindByIdBecauseUserLoginNotExist() throws Exception {
        findUserService.getUserById("nie ma takiego typa");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindByIdBecauseUserLoginIsEmpty() throws Exception {
        findUserService.getUserById("");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenFindByIdBecauseUserLoginIsNull() throws Exception {
        findUserService.getUserById(null);
    }

    @Test
    public void shouldFindUserListWithUserAndAdmin() throws Exception {
        List<UserResource> users = findUserService.getUserList();
        UserResource admin = users.get(0);
        UserResource user = users.get(1);
        assertEquals(users.size(), 2);


        assertEquals(admin.getId(), "9ac1bf56-e499-11e5-9730-9a79f06e9478");
        assertEquals(admin.getLogin(), "admin");
        assertEquals(admin.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(admin.getRole(), Role.ADMIN);
        assertEquals(admin.getFirstName(), "Mateusz");
        assertEquals(admin.getLastName(), "Szczesniak");
        assertEquals(admin.getAddress(), "Skiby 113, 26-060 Checiny");
        assertEquals(admin.getEmail(), "mszczesniak16@gmail.com");
        assertNotNull(admin.getLastPasswordReset());
        assertEquals(admin.getVersion(), new Long(0L));

        assertEquals(user.getId(), "177b9ad8-17b9-11e6-b6ba-3e1d05defe78");
        assertEquals(user.getLogin(), "user");
        assertEquals(user.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(user.getRole(), Role.USER);
        assertEquals(user.getFirstName(), "Mateusz");
        assertEquals(user.getLastName(), "Szczesniak");
        assertEquals(user.getAddress(), "Skiby 113, 26-060 Checiny");
        assertEquals(user.getEmail(), "mszczesniak16spam@gmail.com");
        assertNotNull(user.getLastPasswordReset());
        assertEquals(user.getVersion(), new Long(0L));

    }

    @Test
    public void shouldRegisterNewUser() throws Exception {

        NewUserResource userResource = NewUserResource.newBuilder()
                .withLogin("login")
                .withFirstName("Imie")
                .withLastName("Nazwisko")
                .withAddress("Adres")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withEmail("mszczesniak16test@gmail.com")
                .build();
        registerUserService.registerUser(userResource);

        List<UserResource> users = findUserService.getUserList();
        assertEquals(users.size(), 3);

        UserResource user = findUserService.getUserByLogin("login");
        assertNotNull(user.getId());
        assertEquals(user.getLogin(), "login");
        assertEquals(user.getCityId(), "4154d988-03bd-11e6-b512-3e1d05defe78");
        assertEquals(user.getRole(), Role.USER);
        assertEquals(user.getFirstName(), "Imie");
        assertEquals(user.getLastName(), "Nazwisko");
        assertEquals(user.getAddress(), "Adres");
        assertEquals(user.getEmail(), "mszczesniak16test@gmail.com");
        assertNotNull(user.getLastPasswordReset());
        assertEquals(user.getVersion(), new Long(0L));
    }

    //todo mateusz testy validatorow rejestracji usera, zmiany hasla, usuwania i update


}
