package pl.kielce.tu.worldyouthday.user.validators;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.exceptions.InvalidPasswordException;
import pl.kielce.tu.worldyouthday.user.resources.ChangePasswordResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChangeUserPasswordValidator {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    public void validate(String userId, ChangePasswordResource resource) throws ValidationException {

        if (Strings.isNullOrEmpty(userId)) {
            throw new ValidationException("Invalid id");
        }

        if (!userRepository.exists(userId)) {
            throw new ValidationException(String.format("User with id %s not exist", userId));
        }

        String oldPassword = resource.getOldPassword();

        if (Strings.isNullOrEmpty(oldPassword)) {
            throw new ValidationException("Invalid old password");
        }

        if (Strings.isNullOrEmpty(resource.getNewPassword())) {
            throw new ValidationException("Invalid new password");
        }

        if (!bcryptEncoder.matches(oldPassword, userRepository.findOne(userId).getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(resource.getNewPassword());
        if (!matcher.matches()) {
            throw new ValidationException("Invalid password \n" +
                    "Password must contains one digit from 0-9. \n" +
                    "Password must contains one lowercase characters\n" +
                    "Password must contains one uppercase characters\n" +
                    "Password length at least 6 characters and maximum of 20\t");
        }

    }
}
