package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestDetails;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategory;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategoryRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.converter.PointOfInterestToResourceConverter;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.UpdatePointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.UpdatePointOfInterestDetailsValidator;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.UpdatePointOfInterestValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.HashMap;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@Service
public class UpdatePointOfInterestService {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private PointOfInterestCategoryRepository pointOfInterestCategoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UpdatePointOfInterestValidator updatePointOfInterestValidator;

    @Autowired
    private UpdatePointOfInterestDetailsValidator updatePointOfInterestDetailsValidator;

    @Autowired
    private PointOfInterestToResourceConverter pointOfInterestToResourceConverter;

    @Transactional
    @Versionable(table = VersionTable.POI)
    public PointOfInterestResource updatePointOfInterest(String id, UpdatePointOfInterestResource resource) throws ValidationException {
        updatePointOfInterestValidator.validate(id, resource);

        HashMap<Language, PointOfInterestDetails> details = new HashMap<>();

        for (HashMap.Entry<Language, PointOfInterestDetailsResource> entry : resource.getDetails().entrySet()) {
            updatePointOfInterestDetailsValidator.validate(entry.getValue());
            details.put(entry.getKey(), new PointOfInterestDetails(
                    id,
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getValue().getDescription()
            ));
        }

        PointOfInterest poi = pointOfInterestRepository.findOne(id);
        City city = cityRepository.findOne(resource.getCityId());
        PointOfInterestCategory category = pointOfInterestCategoryRepository.findOne(resource.getCategoryId());

        PointOfInterest updatedPointOfInterest = new PointOfInterest(
                id,
                resource.getLatitude(),
                resource.getLongitude(),
                resource.getVersion(),
                category,
                details,
                city,
                poi.getImages()
        );

        return pointOfInterestToResourceConverter.convert(pointOfInterestRepository.saveAndFlush(updatedPointOfInterest));

    }
}
