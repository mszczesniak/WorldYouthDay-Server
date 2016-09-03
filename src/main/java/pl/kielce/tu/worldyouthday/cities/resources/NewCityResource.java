package pl.kielce.tu.worldyouthday.cities.resources;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewCityResource {

    private final String name;

    @JsonCreator
    public NewCityResource(@JsonProperty("name") String name) {
        this.name = name;
    }

    public NewCityResource(Builder builder) {
        this.name = builder.name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewCityResource that = (NewCityResource) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NewCityResource{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class Builder {

        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public NewCityResource build() {
            return new NewCityResource(this);
        }

    }
}
