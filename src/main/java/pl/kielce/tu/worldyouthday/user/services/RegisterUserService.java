package pl.kielce.tu.worldyouthday.user.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.mail.MailService;
import pl.kielce.tu.worldyouthday.user.RegisterEmail;
import pl.kielce.tu.worldyouthday.user.Role;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.user.converters.UserToResourceConverter;
import pl.kielce.tu.worldyouthday.user.resources.NewUserResource;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.user.validators.CreateUserValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.UUID;

@Service
public class RegisterUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserValidator createUserValidator;

    @Autowired
    private UserToResourceConverter userToResourceConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private MailService mailService;


    @Transactional(rollbackFor = MessagingException.class)
    public UserResource registerUser(NewUserResource userResource) throws ValidationException, MessagingException {

        createUserValidator.validate(userResource);

        City city = cityRepository.findOne(userResource.getCityId());

        String password = RandomStringUtils.randomAlphabetic(8);

        User user = new User(
                UUID.randomUUID().toString(),
                city,
                Role.USER,
                userResource.getLogin(),
                bcryptEncoder.encode(password),
                userResource.getFirstName(),
                userResource.getLastName(),
                userResource.getAddress(),
                userResource.getEmail(),
                true,
                new Date(),
                0L
        );

        User savedUser = userRepository.saveAndFlush(user);
        RegisterEmail registerEmail = new RegisterEmail(savedUser, password);
        mailService.send(registerEmail);

        return userToResourceConverter.convert(savedUser);
    }


}
