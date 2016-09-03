package pl.kielce.tu.worldyouthday.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.validators.RemoveUserValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@Service
public class RemoveUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RemoveUserValidator removeUserValidator;

    public void removeUser(String id) throws ValidationException {
        removeUserValidator.validate(id);
        userRepository.delete(id);
    }
}
