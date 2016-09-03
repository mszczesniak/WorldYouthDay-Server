package pl.kielce.tu.worldyouthday.security.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.worldyouthday.user.converters.UserToResourceConverter;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;

@RestController
public class LoggedUserController {

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public UserResource user(@CurrentlyLoggedUser LoggedUser currentlyLoggedUser) {
        UserToResourceConverter converter = new UserToResourceConverter();
        return converter.convert(currentlyLoggedUser.getUser());
    }
}
