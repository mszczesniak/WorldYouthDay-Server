package pl.kielce.tu.worldyouthday.user;

import pl.kielce.tu.worldyouthday.cities.City;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Size(max = 50)
    @Column(name = "login", nullable = false)
    private String login;

    @Size(max = 250)
    @Column(name = "pass", nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 50)
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "last_password_reset")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordReset;

    @Version
    @Column(name = "version")
    private Long version;

    User() {

    }

    public User(String id, City city, Role role, String login, String password, String firstName, String lastName, String address, String email, boolean active, Date lastPasswordReset, Long version) {
        this.id = id;
        this.city = city;
        this.role = role;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.active = active;
        this.lastPasswordReset = lastPasswordReset;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public Role getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public Long getVersion() {
        return version;
    }
}
