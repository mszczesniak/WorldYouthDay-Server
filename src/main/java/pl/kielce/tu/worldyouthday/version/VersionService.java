package pl.kielce.tu.worldyouthday.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.version.converters.VersionToResourceConverter;
import pl.kielce.tu.worldyouthday.version.resources.VersionResource;

import java.util.List;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private VersionToResourceConverter versionToResourceConverter;

    public List<VersionResource> findVersions() {
        return versionToResourceConverter.convert(versionRepository.findAll());
    }

}
