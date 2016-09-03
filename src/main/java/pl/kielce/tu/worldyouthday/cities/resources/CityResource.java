package pl.kielce.tu.worldyouthday.cities.resources;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class CityResource implements Serializable {

    private final String id;
    private final String name;
    private final Long version;

    public CityResource(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.version = builder.version;
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

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityResource that = (CityResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityResource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }

    public static class Builder {

        private String id;
        private String name;
        private Long version;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public CityResource build() {
            return new CityResource(this);
        }

    }


}
