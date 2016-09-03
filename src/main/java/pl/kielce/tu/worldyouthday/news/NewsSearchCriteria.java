package pl.kielce.tu.worldyouthday.news;

import pl.kielce.tu.worldyouthday.language.Language;

import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 14.05.2016.
 */
public class NewsSearchCriteria {
    private final Optional<Language> language;
    private final Optional<String> cityId;

    public NewsSearchCriteria(Builder builder) {
        language = builder.language;
        cityId = builder.cityId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<Language> getLanguage() {
        return language;
    }

    public Optional<String> getCityId() {
        return cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsSearchCriteria that = (NewsSearchCriteria) o;

        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        return cityId != null ? cityId.equals(that.cityId) : that.cityId == null;

    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsSearchCriteria{" +
                "language=" + language +
                ", cityId=" + cityId +
                '}';
    }

    public static class Builder {
        Optional<Language> language = Optional.empty();
        Optional<String> cityId = Optional.empty();

        public Builder withLanguage(Language language) {
            this.language = Optional.ofNullable(language);
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = Optional.ofNullable(cityId);
            return this;
        }

        public NewsSearchCriteria build() {
            return new NewsSearchCriteria(this);
        }
    }
}
