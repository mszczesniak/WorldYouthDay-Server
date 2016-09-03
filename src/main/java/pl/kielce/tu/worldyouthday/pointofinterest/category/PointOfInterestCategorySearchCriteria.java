package pl.kielce.tu.worldyouthday.pointofinterest.category;


import pl.kielce.tu.worldyouthday.language.Language;

import java.util.Optional;

public class PointOfInterestCategorySearchCriteria {

    private final Optional<Language> language;

    public PointOfInterestCategorySearchCriteria(Builder builder) {
        this.language = builder.language;
    }

    public Optional<Language> getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestCategorySearchCriteria that = (PointOfInterestCategorySearchCriteria) o;

        return !(language != null ? !language.equals(that.language) : that.language != null);

    }

    @Override
    public String toString() {
        return "PointOfInterestCategorySearchCriteria{" +
                "language=" + language +
                '}';
    }

    @Override
    public int hashCode() {
        return language != null ? language.hashCode() : 0;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private Optional<Language> language = Optional.empty();

        public Builder withLanguage(Language language) {
            this.language = Optional.ofNullable(language);
            return this;
        }

        public PointOfInterestCategorySearchCriteria build() {
            return new PointOfInterestCategorySearchCriteria(this);
        }

    }
}
