package pl.kielce.tu.worldyouthday.cities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.cities.resources.NewCityResource;
import pl.kielce.tu.worldyouthday.cities.resources.UpdateCityResource;
import pl.kielce.tu.worldyouthday.cities.services.AddCityService;
import pl.kielce.tu.worldyouthday.cities.services.FindCityService;
import pl.kielce.tu.worldyouthday.cities.services.RemoveCityService;
import pl.kielce.tu.worldyouthday.cities.services.UpdateCityService;

@RestController
@RequestMapping("${spring.data.rest.base-path}/cities")
public class CityController {

    @Autowired
    private FindCityService findCityService;
    @Autowired
    private AddCityService addCityService;
    @Autowired
    private UpdateCityService updateCityService;
    @Autowired
    private RemoveCityService removeCityService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> cityById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(findCityService.getCityById(id), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        try {
            return new ResponseEntity<>(findCityService.getCityList(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody NewCityResource city) {
        try {
            return new ResponseEntity<>(addCityService.addCity(city), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateCityResource city) {
        try {
            return new ResponseEntity<>(updateCityService.updateCity(id, city), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            removeCityService.removeCity(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
