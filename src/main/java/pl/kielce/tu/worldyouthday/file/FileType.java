package pl.kielce.tu.worldyouthday.file;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Łukasz Wesołowski on 10.05.2016.
 */
public enum FileType {
    POI_IMAGE("jpg", "jpeg");

    private List<String> extensions;

    FileType(String ... extensions) {
        this.extensions = Arrays.asList(extensions);
    }

    public boolean containsExtension(String extension) {
        return extensions.contains(extension);
    }
}
