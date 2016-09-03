package pl.kielce.tu.worldyouthday.pointofinterest.converter;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.file.File;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestDetails;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToLanguageResourceConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vooka on 31.03.2016.
 */

@Component
public class PointOfInterestToResourceConverter extends ToLanguageResourceConverter<PointOfInterest, PointOfInterestResource, PointOfInterestDetailsResource> {
    @Override
    public PointOfInterestResource convert(PointOfInterest poi) {
        if (poi == null) {
            return null;
        }

        Map<Language, PointOfInterestDetailsResource> details = new HashMap<>();

        for (HashMap.Entry<Language, PointOfInterestDetails> entry : poi.getDetails().entrySet()) {
            details.put(entry.getKey(), convertDetails(poi, entry.getKey()));
        }

        return PointOfInterestResource
                .newBuilder()
                .withId(poi.getId())
                .withLatitude(poi.getLatitude())
                .withLongitude(poi.getLongitude())
                .withVersion(poi.getVersion())
                .withDetails(details)
                .withCategoryId(poi.getCategory().getId())
                .withCityId(poi.getCity().getId())
                .withImagesId(prepareImagesId(poi))
                .build();
    }

    @Override
    public PointOfInterestResource convert(PointOfInterest poi, Language language) {
        return PointOfInterestResource.newBuilder()
                .withId(poi.getId())
                .withLatitude(poi.getLatitude())
                .withLongitude(poi.getLongitude())
                .withVersion(poi.getVersion())
                .withDetail(poi.getDetails(language).getId().getLanguage(),
                        PointOfInterestDetailsResource
                        .newBuilder()
                        .withName(poi.getDetails(language).getName())
                        .withDescription(poi.getDetails(language).getDescription())
                        .build()
                )
                .withCategoryId(poi.getCategory().getId())
                .withCityId(poi.getCity().getId())
                .withImagesId(prepareImagesId(poi))
                .build();
    }

    private List<String> prepareImagesId(PointOfInterest pointOfInterest) {
        return pointOfInterest.getImages().stream().map(File::getId).collect(Collectors.toList());
    }

    @Override
    public PointOfInterestDetailsResource convertDetails(PointOfInterest poi, Language language) {
        return PointOfInterestDetailsResource
                .newBuilder()
                .withName(poi.getDetails(language).getName())
                .withDescription(poi.getDetails(language).getDescription())
                .build();
    }
}
