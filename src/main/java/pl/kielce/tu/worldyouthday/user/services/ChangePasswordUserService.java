package pl.kielce.tu.worldyouthday.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.converters.UserToResourceConverter;
import pl.kielce.tu.worldyouthday.user.resources.ChangePasswordResource;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.user.validators.ChangeUserPasswordValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@Service
public class ChangePasswordUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserToResourceConverter userToResourceConverter;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private ChangeUserPasswordValidator changeUserPasswordValidator;

    public UserResource changePassword(String id, ChangePasswordResource changePasswordResource) throws ValidationException {

        changeUserPasswordValidator.validate(id, changePasswordResource);

        User user = userRepository.findOne(id);

        User updatedUser = new User(
                id,
                user.getCity(),
                user.getRole(),
                user.getLogin(),
                bcryptEncoder.encode(changePasswordResource.getNewPassword()),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getEmail(),
                user.isActive(),
                user.getLastPasswordReset(),
                user.getVersion()
        );

        return userToResourceConverter.convert(userRepository.saveAndFlush(updatedUser));
    }

}
