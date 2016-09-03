package pl.kielce.tu.worldyouthday.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.scheduler.resources.NewEventResource;
import pl.kielce.tu.worldyouthday.scheduler.resources.UpdateEventResource;
import pl.kielce.tu.worldyouthday.scheduler.services.AddEventService;
import pl.kielce.tu.worldyouthday.scheduler.services.FindEventService;
import pl.kielce.tu.worldyouthday.scheduler.services.RemoveEventService;
import pl.kielce.tu.worldyouthday.scheduler.services.UpdateEventService;

import java.util.Date;

@RestController
@RequestMapping("${spring.data.rest.base-path}/scheduler")
public class SchedulerController {

    @Autowired
    private FindEventService findEventService;

    @Autowired
    private AddEventService addEventService;

    @Autowired
    private UpdateEventService updateEventService;

    @Autowired
    private RemoveEventService removeEventService;

    //Przyklad daty w formacie ISO 2016-05-04T21:30.994-0000
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(
            @RequestParam(value = "language", required = false) Language language,
            @RequestParam(value = "cityId", required = false) String cityId,
            @RequestParam(value = "dateFrom", required = false) Date dateFrom,
            @RequestParam(value = "dateTo", required = false) Date dateTo) {

        SchedulerSearchCriteria searchCriteria = SchedulerSearchCriteria
                .newBuilder()
                .withLanguage(language)
                .withCityId(cityId)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .build();
        try {
            return new ResponseEntity<>(findEventService.getEvents(searchCriteria), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> eventById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(findEventService.getEventForId(id), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewEventResource event) {
        try {
            return new ResponseEntity<>(addEventService.addEvent(event), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@RequestBody UpdateEventResource event) {
        try {
            return new ResponseEntity<>(updateEventService.updateEvent(event), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            removeEventService.removeEvent(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
