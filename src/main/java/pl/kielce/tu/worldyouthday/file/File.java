package pl.kielce.tu.worldyouthday.file;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by Łukasz Wesołowski on 10.05.2016.
 */
@Entity
@Table(name = "FILES")
public class File {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name="content")
    private byte[] content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FileType type;

    @Version
    @Column(name = "version")
    private Long version;

    public File() {
    }

    public File(String id, byte[] content, FileType type, Long version) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public FileType getType() {
        return type;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (id != null ? !id.equals(file.id) : file.id != null) return false;
        if (!Arrays.equals(content, file.content)) return false;
        if (type != file.type) return false;
        return version != null ? version.equals(file.version) : file.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", version=" + version +
                '}';
    }
}
