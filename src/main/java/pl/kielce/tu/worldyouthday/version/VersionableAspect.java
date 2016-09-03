package pl.kielce.tu.worldyouthday.version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class VersionableAspect {

    private static final Log logger = LogFactory.getLog(VersionableAspect.class);

    @Autowired
    private VersionRepository versionRepository;

    @After("@annotation(versionable)")
    public void audit(Versionable versionable) {

        try {
            Optional<Version> versionOptional = versionRepository.findOneByTable(versionable.table());
            if (versionOptional.isPresent()) {
                Version version = versionOptional.get();
                Version newVersion = new Version(version.getTable(), version.getValue() + 1);
                versionRepository.saveAndFlush(newVersion);
            } else {
                Version version = new Version(versionable.table(), 0L);
                versionRepository.saveAndFlush(version);
            }
        } catch (Exception ex) {
            logger.error(String.format("Error occurred when trying to increment version for table %s", versionable.table()));
        }
    }
}
