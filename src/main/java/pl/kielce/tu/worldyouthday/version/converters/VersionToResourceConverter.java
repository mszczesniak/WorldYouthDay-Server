package pl.kielce.tu.worldyouthday.version.converters;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.converter.ToResourceConverter;
import pl.kielce.tu.worldyouthday.version.Version;
import pl.kielce.tu.worldyouthday.version.resources.VersionResource;

@Component
public class VersionToResourceConverter extends ToResourceConverter<Version, VersionResource> {


    @Override
    public VersionResource convert(Version version) {
        return VersionResource
                .newBuilder()
                .withTable(version.getTable())
                .withValue(version.getValue())
                .build();
    }
}
