package pl.kielce.tu.worldyouthday.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${spring.data.rest.base-path}/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Language>> list() {
        return new ResponseEntity<>(languageService.getLanguages(), HttpStatus.OK);
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public ResponseEntity<Language> defaultLanguage() {
        return new ResponseEntity<>(languageService.getDefailtLanguage(), HttpStatus.OK);
    }
}
