package pl.kielce.tu.worldyouthday.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.version.resources.VersionResource;

import java.util.List;

@RestController
@RequestMapping("${spring.data.rest.base-path}/versions")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam(value = "language", required = false) Language language) {

        try {
            List<VersionResource> versions = versionService.findVersions();
            return new ResponseEntity<>(versions, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
