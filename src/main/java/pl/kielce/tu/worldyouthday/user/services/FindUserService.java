package pl.kielce.tu.worldyouthday.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.converters.UserToResourceConverter;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.user.validators.GetUserByIdValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;
import java.util.Optional;

@Service
public class FindUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserToResourceConverter userToResourceConverter;

    @Autowired
    private GetUserByIdValidator getUserByIdValidator;

    public List<UserResource> getUserList() {
        return userToResourceConverter.convert(userRepository.findAll());
    }

    public UserResource getUserById(String id) throws ValidationException {
        getUserByIdValidator.validate(id);
        return userToResourceConverter.convert(userRepository.findOne(id));
    }

    public UserResource getUserByLogin(String login) throws ValidationException {
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.isPresent()) {
            return userToResourceConverter.convert(user.get());
        } else {
            throw new ValidationException(String.format("User with login %s no found", login));
        }
    }
}
