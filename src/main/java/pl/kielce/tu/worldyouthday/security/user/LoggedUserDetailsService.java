package pl.kielce.tu.worldyouthday.security.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;

import java.util.Optional;

@Service
public class LoggedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByLogin(String email) {
        return userRepository.findOneByLogin(email);
    }

    public LoggedUser loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findOneByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with login=%s was not found", login)));
        return new LoggedUser(user);
    }
}
