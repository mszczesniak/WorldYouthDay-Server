package pl.kielce.tu.worldyouthday.file.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;
import pl.kielce.tu.worldyouthday.file.FileType;

/**
 * Created by Łukasz Wesołowski on 17.05.2016.
 */
public class NewFileResource {
    private MultipartFile file;
    private FileType type;

    @JsonCreator
    public NewFileResource(@JsonProperty("file") MultipartFile file,
                           @JsonProperty("type") FileType type) {
        this.file = file;
        this.type = type;
    }

    public NewFileResource(Builder builder) {
        file = builder.file;
        type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public MultipartFile getFile() {
        return file;
    }

    public FileType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewFileResource resource = (NewFileResource) o;

        if (file != null ? !file.equals(resource.file) : resource.file != null) return false;
        return type == resource.type;

    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewFileResource{" +
                "file=" + file +
                ", type=" + type +
                '}';
    }

    public static class Builder {
        private MultipartFile file;
        private FileType type;

        public Builder withFile(MultipartFile file) {
            this.file = file;
            return this;
        }

        public Builder withType(FileType type) {
            this.type = type;
            return this;
        }

        public NewFileResource build() {
            return new NewFileResource(this);
        }
    }
}
