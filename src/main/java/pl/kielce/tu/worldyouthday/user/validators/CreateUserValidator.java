package pl.kielce.tu.worldyouthday.user.validators;

import com.google.common.base.Strings;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.exceptions.UserAlreadyExistException;
import pl.kielce.tu.worldyouthday.user.resources.NewUserResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class CreateUserValidator implements Validator<NewUserResource> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void validate(NewUserResource newUserResource) throws ValidationException, UserAlreadyExistException {

        if (userRepository.findOneByLogin(newUserResource.getLogin()).isPresent()) {
            throw new UserAlreadyExistException("login exist");
        }

        if (userRepository.findOneByEmail(newUserResource.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("email exist");
        }

        if (!cityRepository.exists(newUserResource.getCityId())) {
            throw new ValidationException(String.format("City with id %s not exist", newUserResource.getCityId()));
        }

        if (Strings.isNullOrEmpty(newUserResource.getLogin())) {
            throw new ValidationException("Invalid login");
        }

        if (Strings.isNullOrEmpty(newUserResource.getFirstName())) {
            throw new ValidationException("Invalid first name");
        }

        if (Strings.isNullOrEmpty(newUserResource.getLastName())) {
            throw new ValidationException("Invalid lst name");
        }

        if (Strings.isNullOrEmpty(newUserResource.getAddress())) {
            throw new ValidationException("Invalid address");
        }

        if (Strings.isNullOrEmpty(newUserResource.getEmail())) {
            throw new ValidationException("Invalid email");
        }

        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(newUserResource.getEmail())) {
            throw new ValidationException("Invalid email");
        }
    }
}
