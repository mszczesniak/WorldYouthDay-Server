package pl.kielce.tu.worldyouthday.scheduler;


import pl.kielce.tu.worldyouthday.language.Language;

import java.util.Date;
import java.util.Optional;

public class SchedulerSearchCriteria {

    private final Optional<Language> language;
    private final Optional<String> cityId;
    private final Optional<Date> dateFrom;
    private final Optional<Date> dateTo;

    public SchedulerSearchCriteria(Builder builder) {
        this.language = builder.language;
        this.cityId = builder.cityId;
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
    }

    public Optional<Language> getLanguage() {
        return language;
    }

    public Optional<String> getCityId() {
        return cityId;
    }

    public Optional<Date> getDateFrom() {
        return dateFrom;
    }

    public Optional<Date> getDateTo() {
        return dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchedulerSearchCriteria that = (SchedulerSearchCriteria) o;

        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        return !(dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null);

    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SchedulerSearchCriteria{" +
                "language=" + language +
                ", cityId=" + cityId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private Optional<Language> language = Optional.empty();
        private Optional<String> cityId = Optional.empty();
        private Optional<Date> dateFrom = Optional.empty();
        private Optional<Date> dateTo = Optional.empty();

        public Builder withLanguage(Language language) {
            this.language = Optional.ofNullable(language);
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = Optional.ofNullable(cityId);
            return this;
        }

        public Builder withDateFrom(Date dateFrom) {
            this.dateFrom = Optional.ofNullable(dateFrom);
            return this;
        }

        public Builder withDateTo(Date dateTo) {
            this.dateTo = Optional.ofNullable(dateTo);
            return this;
        }

        public SchedulerSearchCriteria build() {
            return new SchedulerSearchCriteria(this);
        }

    }
}
