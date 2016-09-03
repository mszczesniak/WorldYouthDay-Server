package pl.kielce.tu.worldyouthday.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.resource.NewNewsResource;
import pl.kielce.tu.worldyouthday.news.resource.UpdateNewsResource;
import pl.kielce.tu.worldyouthday.news.service.AddNewsService;
import pl.kielce.tu.worldyouthday.news.service.FindNewsService;
import pl.kielce.tu.worldyouthday.news.service.RemoveNewsService;
import pl.kielce.tu.worldyouthday.news.service.UpdateNewsService;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */

@RestController
@RequestMapping("${spring.data.rest.base-path}/news")
public class NewsController {
    @Autowired
    private FindNewsService findNewsService;

    @Autowired
    private AddNewsService addNewsService;

    @Autowired
    private UpdateNewsService updateNewsService;

    @Autowired
    private RemoveNewsService removeNewsService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam(value = "cityId", required = false) String cityId,
                                  @RequestParam(value = "language", required = false) Language language) {

        NewsSearchCriteria criteria = NewsSearchCriteria.newBuilder()
                .withLanguage(language)
                .withCityId(cityId)
                .build();

        try {

            return new ResponseEntity<>(findNewsService.findAllNews(criteria), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{newsId}")
    public ResponseEntity<?> newsById(@PathVariable(value = "newsId") String id) {
        try {
            return new ResponseEntity<>(findNewsService.findNews(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> addNews(@RequestBody NewNewsResource pointOfInterest) {
        try {
            return new ResponseEntity<>(addNewsService.addNews(pointOfInterest), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity updateNews(@PathVariable String id, @RequestBody UpdateNewsResource resource) {
        try {
            return new ResponseEntity(updateNewsService.updateNews(id, resource), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex, HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeNews(@PathVariable String id) {
        try {
            removeNewsService.removeNews(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex, HttpStatus.CONFLICT);
        }
    }
}
