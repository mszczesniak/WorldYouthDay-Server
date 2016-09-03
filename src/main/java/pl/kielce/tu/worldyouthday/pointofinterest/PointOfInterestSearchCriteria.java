package pl.kielce.tu.worldyouthday.pointofinterest;

import pl.kielce.tu.worldyouthday.language.Language;

import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 14.05.2016.
 */
public class PointOfInterestSearchCriteria {
    private final Optional<Language> language;
    private final Optional<String> cityId;
    private final Optional<String> categoryId;

    public PointOfInterestSearchCriteria(Builder builder) {
        language = builder.language;
        cityId = builder.cityId;
        categoryId = builder.categoryId;
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

    public Optional<String> getCategoryId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestSearchCriteria that = (PointOfInterestSearchCriteria) o;

        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        return categoryId != null ? categoryId.equals(that.categoryId) : that.categoryId == null;

    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestSearchCriteria{" +
                "language=" + language +
                ", cityId=" + cityId +
                ", categoryId=" + categoryId +
                '}';
    }

    public static class Builder {
        private Optional<Language> language = Optional.empty();
        private Optional<String> cityId = Optional.empty();
        private Optional<String> categoryId = Optional.empty();

        public Builder withLanguage(Language language) {
            this.language = Optional.ofNullable(language);
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = Optional.ofNullable(cityId);
            return this;
        }

        public Builder withCategoryId(String categoryId) {
            this.categoryId = Optional.ofNullable(categoryId);
            return this;
        }

        public PointOfInterestSearchCriteria build() {
            return new PointOfInterestSearchCriteria(this);
        }
    }
}
