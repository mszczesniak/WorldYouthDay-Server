package pl.kielce.tu.worldyouthday;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${spring.data.rest.base-path}/")
public class LinksController {

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @RequestMapping(method = RequestMethod.GET)
    public List<Link> list() {

        // SPIS PUBLICZNYCH RESTOW - DLA LUDZI OD ANDROIDA
        List<Link> links = new ArrayList<>();

        // miasta - Mateusz
        links.add(new Link(basePath + "/cities", "Pobranie listy miast"));
        links.add(new Link(basePath + "/cities/{idMiasta}", "Pobranie miasta po id"));

        // jezyki - Mateusz
        links.add(new Link(basePath + "/languages", "Pobranie listy jezykow"));
        links.add(new Link(basePath + "/languages/default", "Pobranie domyslnego jezyka"));

        //ksiazka telefoniczna - Kacper
        links.add(new Link(basePath + "/phones", "Pobranie ksiazki telefonicznej"));

        // newsy - Łukasz
        links.add(new Link(basePath + "/news?cityId=cityId&language=language", "Pobranie newsow - parametry sa nie obowiazkowe"));

        // kategorie zabytków - Łukasz
        links.add(new Link(basePath + "/pointsofinterest/category", "Pobranie kategorii zabytków"));
        links.add(new Link(basePath + "/pointsofinterest/category/{idKategorii}", "Pobranie kategori zabytków po id"));
        links.add(new Link(basePath + "/pointsofinterest/category/{idKategorii}/{JEZYK}", "Pobranie kategori zabytków po id oraz jezyku"));

        //zabytki - Łukasz
        links.add(new Link(basePath + "/pointsofinterest?cityId=cityId&language=language?categoryId=categoryId", "Pobranie zabytków - parametry sa nie obowiazkowe"));
        links.add(new Link(basePath + "/pointsofinterest/{idZabytku}", "Pobranie zabytków po id"));
        links.add(new Link(basePath + "/pointsofinterest/{idZabytku}/{JEZYK}", "Pobranie zabytków po id oraz jezyku"));
        links.add(new Link(basePath + "/pointsofinterest/image", "Dodanie zdjęcia zabytku"));
        links.add(new Link(basePath + "/pointsofinterest/image/{pointOfInterestId}/{imageId}", "Usunięcie zdjęcia zabytku"));
        links.add(new Link(basePath + "/pointsofinterest/image?imageId=imageId", "Pobranie zdjęcia zabytku w postaci pliku jpg"));

        // modlitwy - Mateusz
        links.add(new Link(basePath + "/prayers?language=jezyk", "Pobranie modlitw - parametry sa nie obowiazkowe"));

        // harmonogram dnia - Mateusz
        links.add(new Link(basePath + "/scheduler?language=jezyk", "Pobranie harmonogramu"));
        return links;
    }

    @XmlRootElement
    class Link implements Serializable {

        private String link;
        private String description;

        public Link(String link, String description) {
            this.link = link;
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public String getDescription() {
            return description;
        }


    }
}
