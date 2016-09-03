package pl.kielce.tu.worldyouthday.cities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CITY")
public class City implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected City() {
    }

    public City(String id, String name, Long version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getVersion() {
        return version;
    }
}
