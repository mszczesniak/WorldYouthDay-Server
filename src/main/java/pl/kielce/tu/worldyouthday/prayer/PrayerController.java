package pl.kielce.tu.worldyouthday.prayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerResource;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerResource;
import pl.kielce.tu.worldyouthday.prayer.services.AddPrayerService;
import pl.kielce.tu.worldyouthday.prayer.services.DeletePrayerService;
import pl.kielce.tu.worldyouthday.prayer.services.FindPrayerService;
import pl.kielce.tu.worldyouthday.prayer.services.UpdatePrayerService;

import java.util.List;

@RestController
@RequestMapping("${spring.data.rest.base-path}/prayers")
public class PrayerController {

    @Autowired
    private AddPrayerService addPrayerService;

    @Autowired
    private UpdatePrayerService updatePrayerService;

    @Autowired
    private DeletePrayerService deletePrayerService;

    @Autowired
    private FindPrayerService findPrayerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam(value = "language", required = false) Language language) {

        PrayerSearchCriteria prayerSearchCriteria = PrayerSearchCriteria
                .newBuilder()
                .withLanguage(language)
                .build();

        try {
            List<PrayerResource> prayers = findPrayerService.getPrayers(prayerSearchCriteria);
            return new ResponseEntity<>(prayers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> prayerById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(findPrayerService.getPrayerForId(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewPrayerResource prayer) {
        try {
            return new ResponseEntity<>(addPrayerService.addPrayer(prayer), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdatePrayerResource prayer) {
        try {
            return new ResponseEntity<>(updatePrayerService.updatePrayer(id, prayer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            deletePrayerService.removePrayer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
