package pl.kielce.tu.worldyouthday.user.validators;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.exceptions.UserAlreadyExistException;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class GetUserByIdValidator implements Validator<String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(String id) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (!userRepository.exists(id)) {
            throw new UserAlreadyExistException(String.format("user with id %s not exist", id));
        }
    }
}
