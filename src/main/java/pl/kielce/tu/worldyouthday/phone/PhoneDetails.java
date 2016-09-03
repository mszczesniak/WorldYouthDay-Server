package pl.kielce.tu.worldyouthday.phone;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PHONE_DETAILS")
public class PhoneDetails implements Serializable {

    @EmbeddedId
    private PhoneDetailsPK id;

    @Column(name = "PHD_OWNER", nullable = false)
    private String owner;

    @Column(name = "PHD_DESCRIPTION", nullable = false)
    private String description;

    @Version
    @Column(name = "PHD_VERSION", nullable = false)
    private Long version;

    protected PhoneDetails() {
    }

    public PhoneDetails(Long version, PhoneDetailsPK id, String owner, String description) {
        this.owner = owner;
        this.description = description;
        this.id = id;
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public PhoneDetailsPK getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneDetails that = (PhoneDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneDetails{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }
}
