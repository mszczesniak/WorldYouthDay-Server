package pl.kielce.tu.worldyouthday.user.converters;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToResourceConverter;

@Component
public class UserToResourceConverter extends ToResourceConverter<User, UserResource> {

    @Override
    public UserResource convert(User user) {
        return UserResource
                .newBuilder()
                .withId(user.getId())
                .withCityId(user.getCity().getId())
                .withRole(user.getRole())
                .withLogin(user.getLogin())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withAddress(user.getAddress())
                .withEmail(user.getEmail())
                .withLastPasswordReset(user.getLastPasswordReset())
                .withVersion(user.getVersion())
                .build();
    }
}
