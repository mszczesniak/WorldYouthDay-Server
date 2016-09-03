package pl.kielce.tu.worldyouthday.pointofinterest.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.NewPointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.UpdatePointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@RestController
@RequestMapping("${spring.data.rest.base-path}/pointsofinterest/category")
public class PointOfInterestCategoryController {
    @Autowired
    private PointOfInterestCategoryService pointOfInterestCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(@RequestParam(value = "language", required = false) Language language) {
        PointOfInterestCategorySearchCriteria pointOfInterestCategorySearchCriteria = PointOfInterestCategorySearchCriteria
                .newBuilder()
                .withLanguage(language)
                .build();
        try {
            List<PointOfInterestCategoryResource> pointOfInterestCategoryResourceList = pointOfInterestCategoryService.findAll(pointOfInterestCategorySearchCriteria);
            return new ResponseEntity<>(pointOfInterestCategoryResourceList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{categoryId}")
    public ResponseEntity<?> findById(@PathVariable(value = "categoryId") String categoryId) {
        try {
            PointOfInterestCategoryResource pointOfInterestCategory = pointOfInterestCategoryService.findById(categoryId);
            return new ResponseEntity<>(pointOfInterestCategory, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity add(@RequestBody NewPointOfInterestCategoryResource resource) {
        try {
            return new ResponseEntity(pointOfInterestCategoryService.addPointOfInterestCategory(resource), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity update(@PathVariable String id, @RequestBody UpdatePointOfInterestCategoryResource resource) {
        try {
            return new ResponseEntity(pointOfInterestCategoryService.updatePointOfInterestCategory(id, resource), HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity remove(@PathVariable String id) {
        try {
            pointOfInterestCategoryService.removePointOfInterestCategory(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
