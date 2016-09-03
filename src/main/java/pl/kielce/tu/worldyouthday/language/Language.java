package pl.kielce.tu.worldyouthday.language;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Language {

    ENGLISH("EN"),
    POLISH("PL"),
    SPANISH("ES"),
    FRENCH("FR"),
    ITALIAN("IT"),
    PORTUGUESE("PT"),
    RUSSIAN("RU"),
    GERMAN("DE");

    private String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Language getDefault() {
        return ENGLISH;
    }
}
