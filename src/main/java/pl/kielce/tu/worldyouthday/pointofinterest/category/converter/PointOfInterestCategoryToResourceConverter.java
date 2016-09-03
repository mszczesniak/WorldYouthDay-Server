package pl.kielce.tu.worldyouthday.pointofinterest.category.converter;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategory;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategoryDetails;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToLanguageResourceConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@Component
public class PointOfInterestCategoryToResourceConverter extends ToLanguageResourceConverter<PointOfInterestCategory, PointOfInterestCategoryResource, PointOfInterestCategoryDetailsResource> {
    @Override
    public PointOfInterestCategoryResource convert(PointOfInterestCategory poiCategory) {
        if (poiCategory == null) {
            return null;
        }

        Map<Language, PointOfInterestCategoryDetailsResource> details = new HashMap<>();

        for (HashMap.Entry<Language, PointOfInterestCategoryDetails> entry : poiCategory.getDetails().entrySet()) {
            details.put(entry.getKey(), convertDetails(poiCategory, entry.getKey()));
        }

        return PointOfInterestCategoryResource
                .newBuilder()
                .withId(poiCategory.getId())
                .withDetails(details)
                .withVersion(poiCategory.getVersion())
                .build();
    }

    @Override
    public PointOfInterestCategoryResource convert(PointOfInterestCategory poiCategory, Language language) {
        if (poiCategory == null) {
            return null;
        }

        if (language == null) {
            return convert(poiCategory);
        }

        return PointOfInterestCategoryResource
                .newBuilder()
                .withId(poiCategory.getId())
                .withDetail(poiCategory.getDetails(language).getId().getLanguage(), PointOfInterestCategoryDetailsResource
                        .newBuilder()
                        .withName(poiCategory.getDetails(language).getName())
                        .build()
                )
                .withVersion(poiCategory.getVersion())
                .build();
    }

    @Override
    public PointOfInterestCategoryDetailsResource convertDetails(PointOfInterestCategory poiCategory, Language language) {
        return PointOfInterestCategoryDetailsResource
                .newBuilder()
                .withName(poiCategory.getDetails(language).getName())
                .build();
    }
}
