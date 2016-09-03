package pl.kielce.tu.worldyouthday.cities.resources;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateCityResource {

    private final String name;
    private final Long version;

    @JsonCreator
    public UpdateCityResource(@JsonProperty("name") String name,
                              @JsonProperty("version") Long version) {
        this.name = name;
        this.version = version;
    }

    public UpdateCityResource(Builder builder) {
        this.name = builder.name;
        this.version = builder.version;
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

        UpdateCityResource that = (UpdateCityResource) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateCityResource{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }

    public static class Builder {

        private String name;
        private Long version;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdateCityResource build() {
            return new UpdateCityResource(this);
        }

    }
}
