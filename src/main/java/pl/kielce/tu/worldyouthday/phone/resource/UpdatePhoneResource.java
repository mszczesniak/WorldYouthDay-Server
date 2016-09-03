package pl.kielce.tu.worldyouthday.phone.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class UpdatePhoneResource implements Serializable {
    private Long version;
    private String number;
    private Map<Language, UpdatePhoneDetailsResource> details;
    private List<String> cityIds;

    @JsonCreator
    public UpdatePhoneResource(
            @JsonProperty("version") Long version,
            @JsonProperty("number") String number,
            @JsonProperty("details") Map<Language, UpdatePhoneDetailsResource> details,
            @JsonProperty("cityIds") List<String> cityIds) {
        this.version = version;
        this.number = number;
        this.details = details;
        this.cityIds = cityIds;
    }

    private UpdatePhoneResource(Builder builder) {
        version = builder.version;
        number = builder.number;
        details = builder.details;
        cityIds = builder.cityIds;
    }

    public Long getVersion() {
        return version;
    }

    public String getNumber() {
        return number;
    }

    public Map<Language, UpdatePhoneDetailsResource> getDetails() {
        return details;
    }

    public List<String> getCityIds() {
        return cityIds;
    }

    public static UpdatePhoneResource.Builder newBuilder() { return new UpdatePhoneResource.Builder(); }

    public static class Builder {
        private Long version;
        private String number;
        private Map<Language, UpdatePhoneDetailsResource> details = new HashMap<>();
        private List<String> cityIds = new ArrayList<>();

        public UpdatePhoneResource.Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdatePhoneResource.Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withDetail(Language language, UpdatePhoneDetailsResource detailsResource) {
            this.details.put(language, detailsResource);
            return this;
        }

        public Builder withDetails(Map<Language, UpdatePhoneDetailsResource> details) {
            this.details = details;
            return this;
        }

        public UpdatePhoneResource.Builder withCity(String cityId) {
            this.cityIds.add(cityId);
            return this;
        }

        public UpdatePhoneResource.Builder withCities(List<String> cityIds) {
            this.cityIds = cityIds;
            return this;
        }

        public UpdatePhoneResource build() {
            return new UpdatePhoneResource(this);
        }
    }
}
