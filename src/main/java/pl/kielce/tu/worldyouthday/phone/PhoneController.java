package pl.kielce.tu.worldyouthday.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.phone.resource.NewPhoneResource;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneResource;
import pl.kielce.tu.worldyouthday.phone.services.AddPhoneService;
import pl.kielce.tu.worldyouthday.phone.services.FindPhoneService;
import pl.kielce.tu.worldyouthday.phone.services.RemovePhoneService;
import pl.kielce.tu.worldyouthday.phone.services.UpdatePhoneService;

@RestController
@RequestMapping("${spring.data.rest.base-path}/phones")
public class PhoneController {

    @Autowired
    protected AddPhoneService addPhoneService;

    @Autowired
    protected FindPhoneService findPhoneService;

    @Autowired
    protected RemovePhoneService removePhoneService;

    @Autowired
    protected UpdatePhoneService updatePhoneService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewPhoneResource phoneResource) {
        try {
            return new ResponseEntity<>(addPhoneService.addPhone(phoneResource), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdatePhoneResource phoneResource) {
        try {
            return new ResponseEntity<>(updatePhoneService.updatePhone(id, phoneResource), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            removePhoneService.removePhone(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam(name = "cityId", required = false) String cityId) {
        try {


            PhoneSearchCriteria phoneSearchCriteria =
                    new PhoneSearchCriteria.Builder()
                            .withCityId(cityId)
                            .build();

            return new ResponseEntity<>(findPhoneService.findAll(phoneSearchCriteria), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> byId(@PathVariable String id) {
        try {
            return new ResponseEntity<>(findPhoneService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
}
