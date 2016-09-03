package pl.kielce.tu.worldyouthday.utils.misc;

import pl.kielce.tu.worldyouthday.language.Language;

import java.io.Serializable;

public class LanguageEntry implements Serializable {

    private Long version;
    private Language language;
    private String content;

    public LanguageEntry() {
    }

    public LanguageEntry(Builder builder) {
        version = builder.version;
        language = builder.language;
        content = builder.content;
    }

    public Long getVersion() {
        return version;
    }

    public Language getLanguage() {
        return language;
    }

    public String getContent() {
        return content;
    }

    public static Builder newBuilder() { return new Builder(); }

    public static class Builder {
        private Long version;
        private Language language;
        private String content;

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public Builder withLanguage(Language language) {
            this.language = language;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public LanguageEntry build() { return new LanguageEntry(this); }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageEntry that = (LanguageEntry) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (language != that.language) return false;
        return content != null ? content.equals(that.content) : that.content == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LanguageEntry{" +
                "version=" + version +
                ", language=" + language +
                ", content='" + content + '\'' +
                '}';
    }
}
