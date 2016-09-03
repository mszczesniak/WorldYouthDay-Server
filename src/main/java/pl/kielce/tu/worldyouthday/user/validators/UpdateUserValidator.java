package pl.kielce.tu.worldyouthday.user.validators;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.resources.UpdateUserResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.UpdateValidator;

@Component
public class UpdateUserValidator implements UpdateValidator<UpdateUserResource> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(String userId, UpdateUserResource updateUserResource) throws ValidationException {

        if (Strings.isNullOrEmpty(userId)) {
            throw new ValidationException("Invalid id");
        }

        if (!userRepository.exists(userId)) {
            throw new ValidationException(String.format("User with id %s not exist", userId));
        }

        if (Strings.isNullOrEmpty(updateUserResource.getFirstName())) {
            throw new ValidationException("Invalid first name");
        }

        if (Strings.isNullOrEmpty(updateUserResource.getLastName())) {
            throw new ValidationException("Invalid lst name");
        }

        if (Strings.isNullOrEmpty(updateUserResource.getAddress())) {
            throw new ValidationException("Invalid address");
        }
    }
}
