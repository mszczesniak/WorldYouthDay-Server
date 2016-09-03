package pl.kielce.tu.worldyouthday.security.user;

import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToResourceConverter;

public class LoggedUserToResourceConverter extends ToResourceConverter<User, UserResource> {

    @Override
    public UserResource convert(User user) {
        return UserResource
                .newBuilder()
                .withId(user.getId())
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
