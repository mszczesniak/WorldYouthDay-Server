package pl.kielce.tu.worldyouthday.security.user;

import org.springframework.security.core.authority.AuthorityUtils;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.user.Role;
import pl.kielce.tu.worldyouthday.user.User;

import java.util.Date;

public class LoggedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public LoggedUser(User user) {
        super(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }

    public Date getLastPasswordReset() {
        return user.getLastPasswordReset();
    }

    public String getCityId() {
        City city = user.getCity();
        return city != null ? city.getId() : "";
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
