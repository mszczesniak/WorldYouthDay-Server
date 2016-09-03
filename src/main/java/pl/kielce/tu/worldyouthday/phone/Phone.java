package pl.kielce.tu.worldyouthday.phone;

import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "PHONE")
public class Phone implements Serializable {

    @Id
    @Column(name = "PHO_ID", nullable = false)
    private String id;

    @Version
    @Column(name = "PHO_VERSION", nullable = false)
    private Long version;

    @Column(name = "PHO_NUMBER", nullable = false)
    private String number;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PHD_ID", referencedColumnName = "PHO_ID", insertable = false, updatable = false)
    @MapKey(name = "id.language")
    private Map<Language, PhoneDetails> details = new HashMap<>();

    @ManyToMany
    @JoinTable(
            name = "CITY_PHONE",
            joinColumns = @JoinColumn(name = "CPH_PHONE", referencedColumnName = "PHO_ID"),
            inverseJoinColumns = @JoinColumn(name = "CPH_CITY", referencedColumnName = "ID")
    )
    private List<City> cities = new ArrayList<>();

    protected Phone() {
    }

    public Phone(Long version, String id, String number, Map<Language, PhoneDetails> details, List<City> cities) {
        this.version = version;
        this.id = id;
        this.number = number;
        this.details = details;
        this.cities = cities;
    }

    public Long getVersion() {
        return version;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Map<Language, PhoneDetails> getDetails() {
        return details;
    }

    public PhoneDetails getDetails(Language language) {
        return details.getOrDefault(language, details.get(Language.getDefault()));
    }

    public List<City> getCities() {
        return cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (id != null ? !id.equals(phone.id) : phone.id != null) return false;
        if (version != null ? !version.equals(phone.version) : phone.version != null) return false;
        if (number != null ? !number.equals(phone.number) : phone.number != null) return false;
        if (details != null ? !details.equals(phone.details) : phone.details != null) return false;
        return cities != null ? cities.equals(phone.cities) : phone.cities == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (cities != null ? cities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", number='" + number + '\'' +
                ", details=" + details +
                ", cities=" + cities +
                '}';
    }
}
