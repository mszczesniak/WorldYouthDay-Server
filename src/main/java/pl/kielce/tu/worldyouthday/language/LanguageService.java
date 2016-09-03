package pl.kielce.tu.worldyouthday.language;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LanguageService {

    public List<Language> getLanguages() {
        return Arrays.asList(Language.values());
    }

    public Language getDefailtLanguage() {
        return Language.getDefault();
    }

}