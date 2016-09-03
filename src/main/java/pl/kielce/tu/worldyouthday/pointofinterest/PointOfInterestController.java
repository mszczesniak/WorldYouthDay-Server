package pl.kielce.tu.worldyouthday.pointofinterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.NewPointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.RemovePointOfInterestImageResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.UpdatePointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.service.*;

/**
 * Created by vooka on 31.03.2016.
 */

@RestController
@RequestMapping("${spring.data.rest.base-path}/pointsofinterest")
public class PointOfInterestController {
    @Autowired
    private FindPointOfInterestService findPointOfInterestService;

    @Autowired
    private AddPointOfInterestService addPointOfInterestService;

    @Autowired
    private UpdatePointOfInterestService updatePointOfInterestService;

    @Autowired
    private RemovePointOfInterestService removePointOfInterestService;

    @Autowired
    private PointOfInterestImageService pointOfInterestImageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(
            @RequestParam(value = "cityId", required = false) String cityId,
            @RequestParam(value = "language", required = false) Language language,
            @RequestParam(value = "categoryId", required = false) String categoryId) {

        PointOfInterestSearchCriteria criteria = PointOfInterestSearchCriteria.newBuilder()
                .withCategoryId(categoryId)
                .withCityId(cityId)
                .withLanguage(language)
                .build();

        try {
            return new ResponseEntity<>(findPointOfInterestService.findAllPointsOfInterest(criteria), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> pointOfInterestById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(findPointOfInterestService.findPointOfInterestInAllLanguages(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> add(@RequestBody NewPointOfInterestResource pointOfInterest) {
        try {
            return new ResponseEntity<>(addPointOfInterestService.addPointOfInterest(pointOfInterest), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody UpdatePointOfInterestResource resource) {
        try {
            return new ResponseEntity(updatePointOfInterestService.updatePointOfInterest(id, resource), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> remove(@PathVariable String id) {
        try {
            removePointOfInterestService.removePointOfInterest(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "{pointOfInterestId}/image")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> addImage(@PathVariable("pointOfInterestId") String id, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(pointOfInterestImageService.addPointOfInterestImage(id, file), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/image/{pointOfInterestId}/{imageId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> removeImage(@PathVariable("pointOfInterestId") String pointOfInterestId, @PathVariable("imageId") String imageId) {
        try {
            RemovePointOfInterestImageResource resource = RemovePointOfInterestImageResource.newBuilder()
                    .withPointOfInterestId(pointOfInterestId)
                    .withImageId(imageId)
                    .build();

            return new ResponseEntity<>(pointOfInterestImageService.removePointOfInterestImage(resource), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String id) {
        return pointOfInterestImageService.getPointOfInterestImageContent(id);
    }
}
