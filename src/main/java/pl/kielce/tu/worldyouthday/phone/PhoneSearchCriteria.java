package pl.kielce.tu.worldyouthday.phone;

import pl.kielce.tu.worldyouthday.language.Language;

import java.util.Optional;

public class PhoneSearchCriteria {
    private Optional<Language> language;
    private Optional<String> cityId;

    protected PhoneSearchCriteria(Builder builder) {
        this.language = builder.language;
        this.cityId = builder.cityId;
    }

    public Optional<Language> getLanguage() {
        return language;
    }

    public Optional<String> getCityId() {
        return cityId;
    }

    public static PhoneSearchCriteria.Builder newBuilder() { return new PhoneSearchCriteria.Builder(); }

    public static class Builder {
        private Optional<Language> language = Optional.empty();
        private Optional<String> cityId = Optional.empty();

        public Builder withLanguage(Language language) {
            this.language = Optional.ofNullable(language);
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = Optional.ofNullable(cityId);
            return this;
        }

        public PhoneSearchCriteria build() {
            return new PhoneSearchCriteria(this);
        }
    }
}
