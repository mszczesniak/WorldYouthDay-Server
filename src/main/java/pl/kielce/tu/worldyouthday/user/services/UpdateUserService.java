package pl.kielce.tu.worldyouthday.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.converters.UserToResourceConverter;
import pl.kielce.tu.worldyouthday.user.resources.UpdateUserResource;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.user.validators.UpdateUserValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@Service
public class UpdateUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UpdateUserValidator updateUserValidator;

    @Autowired
    private UserToResourceConverter userToResourceConverter;

    public UserResource updateUser(String id, UpdateUserResource userResource) throws ValidationException {

        updateUserValidator.validate(id, userResource);

        User user = userRepository.findOne(id);

        User updatedUser = new User(
                id,
                user.getCity(),
                user.getRole(),
                user.getLogin(),
                user.getPassword(),
                userResource.getFirstName(),
                userResource.getLastName(),
                userResource.getAddress(),
                user.getEmail(),
                user.isActive(),
                user.getLastPasswordReset(),
                user.getVersion()
        );


        return userToResourceConverter.convert(userRepository.saveAndFlush(updatedUser));
    }

}
